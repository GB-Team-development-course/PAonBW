package ru.gbank.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BalanceDto {

    private Long account;
    private BigDecimal creditBalance;
    private BigDecimal debitBalance;
    private BigDecimal creditDebt;
}
