package ru.gb.core.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.core.converters.AccountConverter;
import ru.gb.core.dto.AccountDto;
import ru.gb.core.entities.Account;
import ru.gb.core.entities.Balance;
import ru.gb.core.enums.AccountStatus;
import ru.gb.core.enums.AccountType;
import ru.gb.core.enums.Currency;
import ru.gb.core.enums.ResponseCode;
import ru.gb.core.exceptions.AccountNotFoundException;
import ru.gb.core.exceptions.BlockAccountException;
import ru.gb.core.exceptions.CloseAccountException;
import ru.gb.core.response.Response;
import ru.gb.core.response.ResponseFactory;
import ru.gb.core.util.AccountUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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
    private final AccountConverter accountConverter;

    @Transactional
    public Response<AccountDto> createCreditAccount(String username, Currency currency, BigDecimal credit) {

        Account account = new Account(
                null,
                clientService.findByUsername(username).get(),
                AccountType.C,
                createAccountNumber(AccountType.C),
                AccountStatus.ACTIVE,
                currency, LocalDateTime.now(),
                null,
                null,
                null
        );

        accountService.create(account);
        balanceOperationService.createCreditBalance(account, credit);

        return ResponseFactory.successResponse(
                ResponseCode.ACCOUNT_OPERATION_COMPLETE,
                accountConverter.entityToDto(account)
        );
    }

    @Transactional
    public Response<AccountDto> createDebitAccount(String username, Currency currency) {

        Account account = new Account(
                null,
                clientService.findByUsername(username).get(),
                AccountType.D,
                createAccountNumber(AccountType.D),
                AccountStatus.ACTIVE,
                currency,
                LocalDateTime.now(),
                null,
                null,
                null
        );
        accountService.create(account);
        balanceOperationService.createDebitBalance(account);

        return ResponseFactory.successResponse(
                ResponseCode.ACCOUNT_OPERATION_COMPLETE,
                accountConverter.entityToDto(account)
        );
    }

    @Transactional
    public Response<AccountDto> blockAccount(String username, String accountNum) {

        Optional<Account> account = accountService.findByClientUsernameAndAccountNumber(username, accountNum);
        if (account.isEmpty()) {
            throw new AccountNotFoundException(String.format("Счет с идентификатором '%s' не найден", accountNum));
        }
        if (account.get().getAccountStatus().equals(AccountStatus.CLOSED)){
            throw new BlockAccountException(String.format("Счет с идентификатором '%s' закрыт. Нельзя заблокировать закрытый счёт", accountNum));
        }

        account.get().setAccountStatus(AccountStatus.BLOCKED);
        account.get().setDtBlocked(LocalDateTime.now());
        return ResponseFactory.successResponse(
                ResponseCode.ACCOUNT_OPERATION_COMPLETE,
                accountConverter.entityToDto(account.get())
        );
    }


    @Transactional
    public Response<AccountDto> closeAccount(String username, String accountNum) {

        Optional<Account> account = accountService.findByClientUsernameAndAccountNumber(username, accountNum);

        if (account.isEmpty()) {
            throw new AccountNotFoundException(String.format("Счет с идентификатором '%s' не найден", accountNum));
        }

        if (!checkClosingPossibility(account.get())) {
            throw new CloseAccountException(String.format("Счет с идентификатором '%s' не найден", accountNum));
        }

        account.get().setAccountStatus(AccountStatus.CLOSED);
        account.get().setDtClosed(LocalDateTime.now());
        return ResponseFactory.successResponse(
                ResponseCode.ACCOUNT_OPERATION_COMPLETE,
                accountConverter.entityToDto(account.get()));

    }

    public Response<List<AccountDto>> findAll(String username) {

        List<AccountDto> accountDtos = accountService.findAll(username)
                .stream()
                .map(accountConverter::entityToDto).toList();

        return ResponseFactory.successResponse(
                ResponseCode.ACCOUNT_OPERATION_COMPLETE,
                accountDtos);

    }

    public Response<AccountDto> findByClientUsernameAndAccountNumber(String username, String accountNum) {

        Optional<Account> account = accountService.findByClientUsernameAndAccountNumber(username, accountNum);

        if (account.isEmpty()) {
            throw new AccountNotFoundException(String.format("Счет с идентификатором '%s' не найден", accountNum));
        }

        return ResponseFactory.successResponse(
                ResponseCode.ACCOUNT_OPERATION_COMPLETE,
                accountConverter.entityToDto(account.get())
        );
    }

    private String createAccountNumber(AccountType accountType) {

        Optional<Account> previusAccount = accountService.findLastCreatedAccountByType(accountType);

        if (previusAccount.isPresent()) {
            return AccountUtils.generateAccountNumber(previusAccount.get().getAccountNumber());
        }

        if (accountType == AccountType.D) {
            return FIRST_DEBIT_ACCOUNT_NUMBER;
        }

        return FIRST_CREDIT_ACCOUNT_NUMBER;

    }

    private boolean checkClosingPossibility(Account account) {
        Optional<Balance> balance = balanceService.findByAccountId(account.getId());

        return balance.isPresent()
                && balance.get().getCreditBalance().compareTo(BigDecimal.ZERO) == 0
                && balance.get().getDebitBalance().compareTo(BigDecimal.ZERO) == 0
                && balance.get().getCreditDebt().compareTo(BigDecimal.ZERO) == 0;
    }

    public List<AccountDto> findAllDebitActiveByDate(LocalDate currentDate) {
        return accountService.findAllDebitActiveByDate(currentDate)
                .stream()
                .map(accountConverter::entityToDto).toList();
    }
}
