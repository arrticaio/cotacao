package org.br.mineracao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.time.LocalDate;

@Jacksonized
@Data
@Builder
@AllArgsConstructor
public class QuotationDTO {

    public LocalDate date;

    public BigDecimal currencyPrice;


}
