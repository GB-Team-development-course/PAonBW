package ru.gb.core.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Запрос на создание счета
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountRequest {

    /**
     * Номер валюта
     */
    private Integer currency;

    /**
     * Размер кредита
     */
    private BigDecimal credit;

}