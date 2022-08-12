package ru.gbank.pabw.core.converters;

import org.springframework.stereotype.Component;
import ru.gbank.pabw.core.entities.Account;
import ru.gbank.pabw.core.entities.Balance;
import ru.gbank.pabw.model.dto.AccountWithBalanceDto;


@Component
public class AccountWithBalanceConverter {

    public AccountWithBalanceDto entityToDto(Account account, Balance balance) {

        AccountWithBalanceDto out = new AccountWithBalanceDto();
        out.setAccountType(account.getAccountType());
        out.setProductName(account.getProduct().getName());
        out.setAccountNumber(account.getAccountNumber());
        out.setAccountStatus(account.getAccountStatus());
        out.setCurrency(account.getCurrency());
        out.setDebitBalance(balance.getDebitBalance());
        out.setCreditBalance(balance.getCreditBalance());
        out.setCreditDebt(balance.getCreditDebt());
        return out;
    }
}

