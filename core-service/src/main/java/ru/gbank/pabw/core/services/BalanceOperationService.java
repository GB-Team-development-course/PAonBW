package ru.gbank.pabw.core.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gbank.pabw.core.entities.Account;
import ru.gbank.pabw.core.entities.Balance;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class BalanceOperationService {
    private final BalanceService balanceService;

    public Balance createDebitBalance(Account account){

        Balance balance = new Balance(
                null,
                account,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                LocalDateTime.now());

        return balanceService.create(balance);

    }

    public Balance createCreditBalance(Account account,BigDecimal credit) {

        Balance balance = new Balance(
                null,
                account,
                credit,
                BigDecimal.ZERO,
                credit,
                LocalDateTime.now());


        return balanceService.create(balance);
    }
}
