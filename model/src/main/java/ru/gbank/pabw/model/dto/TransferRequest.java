package ru.gbank.pabw.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gbank.pabw.model.enums.Currency;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferRequest {

    private String sourceAccount;
    private String targetAccount;
    private BigDecimal amount;
    private Currency currency;
    private String paymentPurpose;
}
