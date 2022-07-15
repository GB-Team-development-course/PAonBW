package ru.gb.core.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.core.entities.Balance;
import ru.gb.core.repositories.balance.BalanceRepository;

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
}
