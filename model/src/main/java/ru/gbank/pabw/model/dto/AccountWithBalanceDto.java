package ru.gbank.pabw.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gbank.pabw.model.enums.AccountStatus;
import ru.gbank.pabw.model.enums.AccountType;
import ru.gbank.pabw.model.enums.Currency;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Модель счёта")
public class AccountWithBalanceDto {

    @Schema(description = "Тип счёта", required = true, example = "С")
    private AccountType accountType;

    @Schema(description = "Название продукта", required = true, example = "Кредитный продукт")
    private String productName;

    @Schema(description = "Номер счёта", required = true, example = "С1234")
    private String accountNumber;

    @Schema(description = "Статус счёта", required = true, example = "ACTIVE")
    private AccountStatus accountStatus;

    @Schema(description = "Валюта", required = true, example = "USD")
    private Currency currency;

    @Schema(description = "Дебетовый баланс", required = true, example = "500.00")
    private BigDecimal debitBalance;

    @Schema(description = "Кредитный баланс", required = true, example = "300.98")
    private BigDecimal creditBalance;

    @Schema(description = "Задолженность перед банком", required = true, example = "1000.00")
    private BigDecimal creditDebt;

}
