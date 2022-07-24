package ru.gbank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

    //todo вынести в общий модуль все dto
    private String accountType;
    private String accountNumber;
    private String accountStatus;
    private String currency;
    private LocalDateTime dtCreated;
    private LocalDateTime dtBlocked;
    private LocalDateTime dtClosed;

}
