package ru.gbank.pabw.core.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gbank.pabw.core.converters.OrderConverter;
import ru.gbank.pabw.core.entities.Account;
import ru.gbank.pabw.core.entities.Balance;
import ru.gbank.pabw.core.entities.Order;
import ru.gbank.pabw.core.exceptions.ValidationProcessException;
import ru.gbank.pabw.model.dto.OrderDtoRequest;
import ru.gbank.pabw.model.dto.OrderDtoResponse;
import ru.gbank.pabw.model.enums.AccountType;
import ru.gbank.pabw.model.enums.ResponseCode;
import ru.gbank.pabw.model.response.Response;
import ru.gbank.pabw.model.response.ResponseFactory;
import ru.gbank.pabw.model.enums.TransferDirection;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ValidationTransferService {
    private final AccountService accountService;
    private final BalanceService balanceService;
    private final TransferOperationService transferOperationService;
    private final OrderConverter orderConverter;

    @Transactional
    public Response<OrderDtoResponse> validateTransfer(String username, OrderDtoRequest orderDtoRequest) {
        List<String> errors = new ArrayList<>();

        Optional<Account> targetAccount = validateTargetAccount(orderDtoRequest.getTargetAccount());

        if (targetAccount.isEmpty()) {
            errors.add("Аккаунт получатель не найден!");
        } else {
            if (checkAccountIsNotBlocked(targetAccount.get())) {
                errors.add("Аккаунт заблокирован");
            }
            if (checkAccountIsNotClosed(targetAccount.get())) {
                errors.add("Аккаунт закрыт");
            }
            if (checkBalanceOfAccountIsNotExists(targetAccount.get())) {
                errors.add("Баланс отсутствует у получателя");
            }
        }
        Optional<Account> sourceAccount = validateSourceAccount(orderDtoRequest.getSourceAccount(), username);

        if (sourceAccount.isEmpty()) {
            errors.add("Аккаунт отправитель не найден!");
        } else {
            if (targetAccount.isPresent()
                    && sourceAccount.get().getAccountNumber().equals(targetAccount.get().getAccountNumber())) {
                errors.add("Перевод доступен только на другой счет !");
            }
            //Сравнение суммы перевода и баланс аккаунта, если аккаунт найден

            if (checkAccountIsNotBlocked(sourceAccount.get())) {
                errors.add("Аккаунт заблокирован");
            }

            if (checkAccountIsNotClosed(sourceAccount.get())) {
                errors.add("Аккаунт закрыт");
            }

            if (checkBalanceOfAccountIsNotExists(sourceAccount.get())) {
                errors.add("Баланс отсутствует у отправителя");
            }

            if (!doValidationOfAccountBalanceByAccount(sourceAccount.get(), orderDtoRequest.getAmount())) {
                errors.add("Баланс меньше суммы перевода!");
            }
        }

        if (errors.isEmpty()) {
            Order order = new Order();
            order.setSourceAccount(sourceAccount.get().getAccountNumber());
            order.setTargetAccount(targetAccount.get().getAccountNumber());
            order.setExternalOrderGuid(UUID.randomUUID());
            order.setExecutionStart(LocalDateTime.now());
            order.setCurrency(orderDtoRequest.getCurrency());
            order.setTransferDirection(getTransferDirectionByAccountsType(sourceAccount.get(), targetAccount.get()));
            order.setPaymentPurpose(orderDtoRequest.getPaymentPurpose());
            order.setAmount(orderDtoRequest.getAmount());
            return ResponseFactory.successResponse(
                    ResponseCode.ACCOUNT_OPERATION_COMPLETE,
                    orderConverter.entityToDto(
                            transferOperationService.doTransferByOrder(sourceAccount.get(), targetAccount.get(), order)
                    )
            );
        } else {
            throw new ValidationProcessException(errors);
        }
    }

    private boolean checkBalanceOfAccountIsNotExists(Account account) {
        return balanceService.findByAccountId(account.getId()).isEmpty();
    }

    private TransferDirection getTransferDirectionByAccountsType(Account sourceAccount, Account targetAccount) {
        AccountType sourceTransfer = sourceAccount.getAccountType();
        AccountType targetTransfer = targetAccount.getAccountType();

        if (sourceTransfer.equals(AccountType.C)) {
            if (targetTransfer.equals(AccountType.D)) {
                return TransferDirection.C_TO_D;
            } else if (targetTransfer.equals(AccountType.C)) {
                return TransferDirection.C_TO_C;
            }
        } else {
            if (targetTransfer.equals(AccountType.C)) {
                return TransferDirection.D_TO_C;
            }
        }
        return TransferDirection.D_TO_D;
    }

    private boolean checkAccountIsNotBlocked(Account account) {
        return account.getDtBlocked() == null;
    }

    private boolean checkAccountIsNotClosed(Account account) {
        return account.getDtClosed() == null;
    }

    //Проверка баланса
    private boolean doValidationOfAccountBalanceByAccount(Account account, BigDecimal valueTransfer) {
        Optional<Balance> balance = balanceService.findByAccountId(account.getId());
        if (balance.isPresent()) {
            if (account.getAccountType().compareTo(AccountType.C) == 0) {
                return balance.get().getCreditBalance().add(balance.get().getCreditDebt()).compareTo(valueTransfer) >= 0;
            } else {
                return balance.get().getDebitBalance().compareTo(valueTransfer) >= 0;
            }
        } else {
            return false;
        }
    }

    private Optional<Account> validateSourceAccount(String accountSourceNumber, String username) {
        return accountService.findByClientUsernameAndAccountNumber(username, accountSourceNumber);
    }

    private Optional<Account> validateTargetAccount(String accountSourceNumber) {
        return accountService.findByAccountNumber(accountSourceNumber);
    }
}
