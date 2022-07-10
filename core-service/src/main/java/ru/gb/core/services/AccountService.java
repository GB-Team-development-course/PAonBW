package ru.gb.core.services;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import ru.gb.core.entities.Account;
import ru.gb.core.enums.AccountType;
import ru.gb.core.repositories.account.AccountRepository;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public Optional<Account> findByClientIdAndAccountNumber(Long clientId,String accountNum) {
        return accountRepository.findByClientIdAccountByAccountNumber(clientId,accountNum);
    }

    public List<Account> findAll(Long clientId) {
        return accountRepository.findAllByClientId(clientId);
    }


    @NonNull
    public Optional<Account> findLastCreatedAccountByType(@NonNull final AccountType accountType) {
        return accountRepository.findFirstByAccountTypeOrderByDtCreatedDesc(accountType);
    }

    @NonNull
    public Account create(@NonNull final Account account) {
        return accountRepository.save(account);
    }
}
