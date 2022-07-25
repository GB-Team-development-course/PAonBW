package ru.gbank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gbank.enums.Currency;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

    private Long accountId;
    private Long productId;
    private String accountType;
    private String accountNumber;
    private String accountStatus;
    private Currency currency;
    private LocalDateTime dtCreated;
    private LocalDateTime dtBlocked;
    private LocalDateTime dtClosed;

}
