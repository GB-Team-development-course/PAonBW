package ru.gbank.pabw.core.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gbank.pabw.core.entities.Balance;
import ru.gbank.pabw.core.repositories.balance.BalanceRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BalanceService {
    private final BalanceRepository balanceRepository;

    public Balance create(Balance balance) {
        return balanceRepository.save(balance);
    }

    public Optional<Balance> findByAccountId(Long accountId) {
        return balanceRepository.findByAccountId(accountId);
    }

    public Balance save(Balance balance) {
        return balanceRepository.save(balance);
    }

    public List<Balance> findAll(){
        return balanceRepository.findAll();
    }
}
