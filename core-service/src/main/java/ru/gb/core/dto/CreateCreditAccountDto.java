package ru.gb.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
public class CreateCreditAccountDto {

    private Integer currency;
    private BigDecimal credit;

}
