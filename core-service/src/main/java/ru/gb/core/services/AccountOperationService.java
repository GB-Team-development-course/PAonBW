package ru.gb.core.services;

import ch.qos.logback.core.joran.conditional.IfAction;
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
import ru.gb.core.response.Response;
import ru.gb.core.response.ResponseFactory;
import ru.gb.core.util.AccountUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccountOperationService {

    private final static String FIRST_DEBIT_ACCOUNT_NUMBER = "D000000001";
    private final static String FIRST_CREDIT_ACCOUNT_NUMBER = "C000000001";
    private final AccountService accountService;
    private final BalanceOperationService balanceOperationService;
    private final BalanceService balanceService;
    private final AccountConverter accountConverter;

    @Transactional
    public Response<AccountDto> createCreditAccount(String username, Currency currency, BigDecimal credit) {

        try {
            Account account = new Account(
                    null,
                    username,
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

            return ResponseFactory.successResponse(
                    ResponseCode.ACCOUNT_OPERATION_COMPLETE,
                    accountConverter.entityToDto(account)
            );
        } catch (Exception e) {
            return ResponseFactory.errorResponse(ResponseCode.TRANSFER_EXECUTION_ERROR, e.getMessage());
        }

    }

    @Transactional
    public Response<AccountDto> createDebitAccount(String username, Currency currency) {

        try {

            Account account = new Account(
                    null,
                    username,
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

            return ResponseFactory.successResponse(
                    ResponseCode.ACCOUNT_OPERATION_COMPLETE,
                    accountConverter.entityToDto(account)
            );

        } catch (Exception e) {
            return ResponseFactory.errorResponse(ResponseCode.TRANSFER_EXECUTION_ERROR, e.getMessage());
        }
    }

    @Transactional
    public Response<AccountDto> blockAccount(String username, String accountNum) {

        try {

            Optional<Account> account = accountService.findByClientUsernameAndAccountNumber(username, accountNum);
            if (account.isEmpty()) {
                return ResponseFactory.errorResponse(
                        ResponseCode.ACCOUNT_NOT_FOUND,
                        String.format("Счет с идентификатором '%s' не найден", accountNum)
                );

            } else {
                account.get().setAccountStatus(AccountStatus.BLOCKED);
                account.get().setDtBlocked(LocalDateTime.now());
                return ResponseFactory.successResponse(
                        ResponseCode.ACCOUNT_OPERATION_COMPLETE,
                        accountConverter.entityToDto(account.get())
                );
            }

        } catch (Exception e) {
            return ResponseFactory.errorResponse(ResponseCode.TRANSFER_EXECUTION_ERROR, e.getMessage());
        }
    }

    @Transactional
    public Response<AccountDto> closeAccount(String username, String accountNum) {

        try {

            Optional<Account> account = accountService.findByClientUsernameAndAccountNumber(username, accountNum);

            if (account.isEmpty()) {
                return ResponseFactory.errorResponse(
                        ResponseCode.ACCOUNT_NOT_FOUND,
                        String.format("Счет с идентификатором '%s' не найден", accountNum)
                );

            } else if (!checkClosingPossibility(account.get())) {
                return ResponseFactory.errorResponse(
                        ResponseCode.ACCOUNT_CLOSED_ERROR,
                        String.format("Невозможно заблокировать счёт '%s'. Имеются остатки на балансе", accountNum)
                );

            } else {
                account.get().setAccountStatus(AccountStatus.CLOSED);
                account.get().setDtClosed(LocalDateTime.now());
                return ResponseFactory.successResponse(
                        ResponseCode.ACCOUNT_OPERATION_COMPLETE,
                        accountConverter.entityToDto(account.get()));
            }

        } catch (Exception e) {
            return ResponseFactory.errorResponse(ResponseCode.TRANSFER_EXECUTION_ERROR, e.getMessage());
        }
    }

    public Response<List<AccountDto>> findAll(String username) {

        try {

            List<AccountDto> accountDtos = accountService.findAll(username)
                    .stream()
                    .map(accountConverter::entityToDto).toList();

            return ResponseFactory.successResponse(
                    ResponseCode.ACCOUNT_OPERATION_COMPLETE,
                    accountDtos);

        } catch (Exception e) {
            return ResponseFactory.errorResponse(ResponseCode.TRANSFER_EXECUTION_ERROR, e.getMessage());
        }
    }

    public Response<AccountDto> findByClientUsernameAndAccountNumber(String username, String accountNum) {
        try {

            Optional<Account> account = accountService.findByClientUsernameAndAccountNumber(username, accountNum);
            if (account.isEmpty()) {
                return ResponseFactory.errorResponse(
                        ResponseCode.ACCOUNT_NOT_FOUND,
                        String.format("Счет с идентификатором '%s' не найден", accountNum));
            } else {
                return ResponseFactory.successResponse(
                        ResponseCode.ACCOUNT_OPERATION_COMPLETE,
                        accountConverter.entityToDto(account.get())
                );
            }

        } catch (Exception e) {
            return ResponseFactory.errorResponse(ResponseCode.TRANSFER_EXECUTION_ERROR, e.getMessage());
        }
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
