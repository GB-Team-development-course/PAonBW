package ru.gbank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.gbank.enums.Currency;

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
