package org.br.mineracao.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.br.mineracao.client.CurrencyPriceClient;
import org.br.mineracao.dto.CurrencyPriceDTO;
import org.br.mineracao.dto.QuotationDTO;
import org.br.mineracao.entity.QuotationEntity;
import org.br.mineracao.message.KafkaEvents;
import org.br.mineracao.repository.QuotationRepository;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class QuotationService {

    @Inject
    @RestClient
    CurrencyPriceClient currencyPriceClient;

    @Inject
    QuotationRepository quotationRepository;

    @Inject
    KafkaEvents kafkaEvents;

    public void getCurrencyPrice(){

        CurrencyPriceDTO currencyPriceDTO =  currencyPriceClient.getPricesByPair("USD-BRL");

        if(currencyPriceDTO.getUSDBRL() != null){
            if(updateCurrentInfoPrice(currencyPriceDTO)){
                kafkaEvents.sendNewKafkaEvent(QuotationDTO
                        .builder()
                        .currencyPrice(new BigDecimal(currencyPriceDTO.getUSDBRL().getBid()))
                        .date(LocalDate.now())
                        .build());
            }
        }

    }

    private boolean updateCurrentInfoPrice(CurrencyPriceDTO currencyPriceDTO) {

        BigDecimal currentPrice = new BigDecimal(currencyPriceDTO.getUSDBRL().getBid());
        Boolean updatePrice = false;

        List<QuotationEntity> quotationEntityList = quotationRepository.findAll().list();

        if(quotationEntityList.isEmpty()){
            saveQuotation(currencyPriceDTO);
            updatePrice = true;
        }else{
            QuotationEntity lastDollarPrice = quotationEntityList
                    .get(quotationEntityList.size() - 1);

            if(currentPrice.floatValue() > lastDollarPrice.getCurrencyPrice().floatValue()){
                updatePrice = true;
                saveQuotation(currencyPriceDTO);
            }
        }

        return updatePrice;

    }

    private void saveQuotation(CurrencyPriceDTO currencyPriceDTO) {

        QuotationEntity quotation = new QuotationEntity();

        quotation.setDate(LocalDate.now());
        quotation.setCurrencyPrice(new BigDecimal(currencyPriceDTO.getUSDBRL().getBid()));
        quotation.setPctChange(currencyPriceDTO.getUSDBRL().getPctChange());
        quotation.setPair("USD-BRL");

        quotationRepository.persist(quotation);

    }


}
