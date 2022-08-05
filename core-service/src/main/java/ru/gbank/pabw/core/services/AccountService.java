package ru.gbank.pabw.core.services;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import ru.gbank.pabw.core.entities.Account;
import ru.gbank.pabw.core.repositories.account.AccountRepository;
import ru.gbank.pabw.model.enums.AccountType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public Optional<Account> findByClientUsernameAndAccountNumber(String username, String accountNum) {
        return accountRepository.findByClientUsernameAccountByAccountNumber(username, accountNum);
    }

    public Optional<Account> findByAccountNumber(String accountNum) {
        return accountRepository.findAccountByAccountNumber(accountNum);
    }

    public List<Account> findAll(String username) {
        return accountRepository.findAllByClientUsername(username);
    }

    @NonNull
    public Optional<Account> findLastCreatedAccountByType(@NonNull final AccountType accountType) {
        return accountRepository.findFirstByAccountTypeOrderByDtCreatedDesc(accountType);
    }

    @NonNull
    public Account create(@NonNull final Account account) {
        return accountRepository.save(account);
    }

    public List<Account> findAllDebitActiveByDate(LocalDate currentDate) {
        return accountRepository.findAllActiveByDate(LocalDateTime.of(currentDate,LocalTime.MIN),AccountType.D);
    }

    public List<Account> findAllCreditActiveByDate(LocalDate currentDate) {
        return accountRepository.findAllActiveByDate(LocalDateTime.of(currentDate,LocalTime.MIN),AccountType.C);
    }
}
