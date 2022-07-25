package ru.gb.core.converters;

import org.springframework.stereotype.Component;
import ru.gb.core.dto.BalanceDto;
import ru.gb.core.entities.Balance;


@Component
public class BalanceConverter {

    public BalanceDto entityToDto(Balance balance) {
        BalanceDto out = new BalanceDto();
        out.setAccount(balance.getId());
        out.setCreditBalance(balance.getCreditBalance());
        out.setDebitBalance(balance.getDebitBalance());
        out.setCreditDebt(balance.getCreditDebt());
        return out;
    }
}
