package org.br.mineracao.message;

import jakarta.enterprise.context.ApplicationScoped;
import org.br.mineracao.dto.QuotationDTO;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class KafkaEvents {

    private final Logger LOG = LoggerFactory.getLogger(KafkaEvents.class);

    @Channel("quotation-channel")
    Emitter<QuotationDTO> quotationRequestEmitter;

    public void sendNewKafkaEvent(QuotationDTO quotation){
        LOG.info("-- Enviando Cotacao para o Topico Kafka --");
        quotationRequestEmitter.send(quotation).toCompletableFuture().join();
    }
}
