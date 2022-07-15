package ru.gb.core.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.core.entities.Account;
import ru.gb.core.entities.Balance;
import ru.gb.core.enums.AccountStatus;
import ru.gb.core.enums.AccountType;
import ru.gb.core.enums.Currency;
import ru.gb.core.util.AccountUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountOperationService {

    private final static String FIRST_DEBIT_ACCOUNT_NUMBER = "D000000001";
    private final static String FIRST_CREDIT_ACCOUNT_NUMBER = "C000000001";
    private final AccountService accountService;
    private final BalanceOperationService balanceOperationService;
    private final ClientService clientService;
    private final BalanceService balanceService;

    @Transactional
    public Account createCreditAccount(String username, Currency currency, BigDecimal credit) {

        Account account = new Account(
                null,
                clientService.findByUsername(username).get(),
                AccountType.C,
                creteAccountNumber(AccountType.C),
                AccountStatus.ACTIVE,
                currency, LocalDateTime.now(),
                null,
                null,
                null
        );

        accountService.create(account);
        balanceOperationService.createCreditBalance(account, credit);

        return account;

    }

    @Transactional
    public Account createDebitAccount(String username, Currency currency) {

        Account account = new Account(
                null,
                clientService.findByUsername(username).get(),
                AccountType.D,
                creteAccountNumber(AccountType.D),
                AccountStatus.ACTIVE,
                currency, LocalDateTime.now(),
                null,
                null,
                null
        );


        accountService.create(account);
        balanceOperationService.createDebitBalance(account);

        return account;
    }

    @Transactional
    public Optional<Account> blockAccount(String username, String accountNum) {
        Optional<Account> account = accountService.findByClientUsernameAndAccountNumber(username, accountNum);
        if (account.isPresent()) {
            account.get().setAccountStatus(AccountStatus.BLOCKED);
            account.get().setDtBlocked(LocalDateTime.now());
        } else {
            //todo прокинуть исключение или отрицательный ответ
        }
        return account;
    }

    @Transactional
    public Optional<Account> closeAccount(String username, String accountNum) {
        Optional<Account> account = accountService.findByClientUsernameAndAccountNumber(username, accountNum);
        if (account.isPresent() && checkClosingPossibility(account.get())) {
            account.get().setAccountStatus(AccountStatus.CLOSED);
            account.get().setDtClosed(LocalDateTime.now());
        } else {
            //todo прокинуть исключение или отрицательный ответ
        }
        return account;
    }

    private String creteAccountNumber(AccountType accountType) {

        Optional<Account> previusAccount = accountService.findLastCreatedAccountByType(accountType);

        if (previusAccount.isPresent()) {
            return AccountUtils.generateAccountNumber(previusAccount.get().getAccountNumber());

        } else if (accountType == AccountType.D) {
            return FIRST_DEBIT_ACCOUNT_NUMBER;

        } else {
            return FIRST_CREDIT_ACCOUNT_NUMBER;
        }
    }

    private boolean checkClosingPossibility(Account account) {
        Optional<Balance> balance = balanceService.findByAccountId(account.getId());

        return balance.isPresent()
                && balance.get().getCreditBalance().compareTo(BigDecimal.ZERO) == 0
                && balance.get().getDebitBalance().compareTo(BigDecimal.ZERO) == 0
                && balance.get().getCreditDebt().compareTo(BigDecimal.ZERO) == 0;
    }
}
