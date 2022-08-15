package ru.gbank.pabw.core.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gbank.pabw.core.converters.AccountConverter;
import ru.gbank.pabw.core.converters.AccountWithBalanceConverter;
import ru.gbank.pabw.core.entities.Account;
import ru.gbank.pabw.core.entities.Balance;
import ru.gbank.pabw.model.dto.AccountWithBalanceDto;
import ru.gbank.pabw.model.dto.CreateAccountRequest;
import ru.gbank.pabw.model.enums.AccountType;
import ru.gbank.pabw.model.enums.ResponseCode;
import ru.gbank.pabw.model.response.Response;
import ru.gbank.pabw.model.response.ResponseFactory;
import ru.gbank.pabw.core.exceptions.BlockAccountException;
import ru.gbank.pabw.core.exceptions.CloseAccountException;
import ru.gbank.pabw.model.dto.AccountDto;
import ru.gbank.pabw.model.enums.AccountStatus;
import ru.gbank.pabw.core.utils.AccountUtils;
import ru.gbank.pabw.core.exceptions.AccountNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
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
    private final ProductService productService;
    private final AccountWithBalanceConverter accountWithBalanceConverter;

    @Transactional
    public Response<AccountDto> createAccount(String username, CreateAccountRequest createAccountRequest ) {

        Account account = new Account(
                null,
                username,
                createAccountRequest.getAccountType(),
                createAccountNumber(createAccountRequest.getAccountType()),
                productService.findById(createAccountRequest.getProductId()).get(),
                AccountStatus.ACTIVE,
                createAccountRequest.getCurrency(),
                LocalDateTime.now(),
                null,
                null,
                null
        );

        accountService.create(account);

        if (createAccountRequest.getAccountType()==AccountType.D){
            balanceOperationService.createDebitBalance(account);
        }

        if (createAccountRequest.getAccountType()==AccountType.C){

            balanceOperationService.createCreditBalance(account, createAccountRequest.getCredit());

            Balance technicalBalance = balanceService.findByAccountNumber(
                    createAccountRequest.getCurrency().getTechnicalAccountNumber()).get();

            technicalBalance.setDebitBalance(technicalBalance.getDebitBalance().subtract(
                    createAccountRequest.getCredit()));
        }

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
            throw new CloseAccountException(String.format("Счет '%s' нельзя закрыть, на нём ещё есть деньги!", accountNum));
        }

        account.get().setAccountStatus(AccountStatus.CLOSED);
        account.get().setDtClosed(LocalDateTime.now());
        return ResponseFactory.successResponse(
                ResponseCode.ACCOUNT_OPERATION_COMPLETE,
                accountConverter.entityToDto(account.get()));

    }

    @Transactional
    public Response<List<AccountWithBalanceDto>> findAll(String username) {


        Map<Long, Balance> balances = balanceService
                .findAll()
                .stream()
                .collect(Collectors.toMap(b -> b.getAccount().getId(), Function.identity()));


        List<AccountWithBalanceDto> accountDtos = accountService.findAll(username)
                .stream()
                .map(a -> accountWithBalanceConverter.entityToDto(a, balances.get(a.getId())))
                .toList();

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

    public List<AccountDto> findAllActiveByDate(AccountType accountType, LocalDate currentDate) {

        List<Account> accounts = null;

        if (accountType==AccountType.D){
            accounts = accountService.findAllDebitActiveByDate(currentDate);
        }
        if (accountType==AccountType.C){
            accounts = accountService.findAllCreditActiveByDate(currentDate);
        }

        return accounts
                .stream()
                .map(accountConverter::entityToDto).toList();
    }
}
