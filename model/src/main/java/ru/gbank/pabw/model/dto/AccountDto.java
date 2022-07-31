package ru.gbank.pabw.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NoArgsConstructor;
import ru.gbank.pabw.model.enums.AccountType;
import ru.gbank.pabw.model.enums.AccountStatus;
import ru.gbank.pabw.model.enums.Currency;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Модель счёта")
public class AccountDto {

    private Long accountId;
    private String username;
    @Schema(description = "Тип счёта", required = true, example = "С")
    private AccountType accountType;
    private Long productId;
    @Schema(description = "Номер счёта", required = true, example = "С1234")
    private String accountNumber;
    @Schema(description = "Статус счёта", required = true, example = "ACTIVE")
    private AccountStatus accountStatus;
    @Schema(description = "Валюта", required = true, example = "USD")
    private Currency currency;
    @Schema(description = "Дата и время создания счёта", required = true)
    private LocalDateTime dtCreated;
    @Schema(description = "Дата и время блокировки счёта")
    private LocalDateTime dtBlocked;
    @Schema(description = "Дата и время закрытия счёта")
    private LocalDateTime dtClosed;

}
