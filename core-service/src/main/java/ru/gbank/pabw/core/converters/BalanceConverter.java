package ru.gbank.pabw.core.converters;

import org.springframework.stereotype.Component;
import ru.gbank.pabw.core.entities.Balance;
import ru.gbank.pabw.model.dto.BalanceDto;


@Component
public class BalanceConverter {

    public BalanceDto entityToDto(Balance balance) {
        BalanceDto out = new BalanceDto();
        out.setAccount(balance.getAccount().getId());
        out.setCreditBalance(balance.getCreditBalance());
        out.setDebitBalance(balance.getDebitBalance());
        out.setCreditDebt(balance.getCreditDebt());
        return out;
    }
}
