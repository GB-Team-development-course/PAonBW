package ru.gb.core.dto;
/* 
11.07.2022: Alexey created this file inside the package: ru.gb.core.dto 
*/

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.gb.core.enums.Currency;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class OrderDtoRequest {

    private String sourceAccount;
    private String targetAccount;
    private BigDecimal amount;
    private Currency currency;
    private String paymentPurpose;
}
