package ru.gb.core.services;
/* 
14.07.2022: Alexey created this file inside the package: ru.gb.core.services 
*/

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.core.converters.OrderConverter;
import ru.gb.core.dto.OrderDtoRequest;
import ru.gb.core.dto.OrderDtoResponse;
import ru.gb.core.entities.Account;
import ru.gb.core.entities.Balance;
import ru.gb.core.entities.Order;
import ru.gb.core.enums.AccountType;
import ru.gb.core.enums.ResponseCode;
import ru.gb.core.enums.TransferDirection;
import ru.gb.core.exceptions.ValidationProcessException;
import ru.gb.core.response.Response;
import ru.gb.core.response.ResponseFactory;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
        try {
            Account sourceAccount = accountService.findByClientUsernameAndAccountNumber(
                    username, orderDtoRequest.getSourceAccount()).orElseThrow(
                    () -> new ValidationProcessException("Аккаунт не найден!")
            );

            Account targetAccount = accountService.findByAccountNumber(
                    orderDtoRequest.getTargetAccount()).orElseThrow(
                    () -> new ValidationProcessException("Аккаунт адресант не найден!")
            );

            if (sourceAccount.getAccountNumber().equals(targetAccount.getAccountNumber())) {
                throw new ValidationProcessException("Перевод доступен только на другой счет!");
            }

            if (!doValidationOfAccountBalanceByAccount(sourceAccount, orderDtoRequest.getAmount())) {
                throw new ValidationProcessException("Баланс меньше суммы перевода!");
            }

            Order order = new Order();
            order.setSourceAccount(sourceAccount.getAccountNumber());
            order.setTargetAccount(targetAccount.getAccountNumber());
            order.setExternalOrderGuid(UUID.randomUUID());
            order.setExecutionStart(LocalDateTime.now());
            order.setCurrency(orderDtoRequest.getCurrency());
            order.setTransferDirection(TransferDirection.D_TO_D); //TODO findTransferDirection
            order.setPaymentPurpose(orderDtoRequest.getPaymentPurpose());
            order.setAmount(orderDtoRequest.getAmount());

            return ResponseFactory.successResponse(ResponseCode.OPERATION_COMPLETE,
                    orderConverter.entityToDto(
                            transferOperationService.doTransferByOrder(sourceAccount, targetAccount, order))
            );
        } catch (Exception e) {
            return ResponseFactory.errorResponse(ResponseCode.ACCOUNT_VALIDATION_ERROR, e.getMessage());
        }
    }

    //TODO checkAccountIsBlocked and checkAccountIsClosed

    //Метод валидации баланса
    private boolean doValidationOfAccountBalanceByAccount(Account account, BigDecimal valueTransfer) {
        Balance balance = balanceService.findByAccountId(account.getId()).orElseThrow(
                () -> new ValidationProcessException("Ошибка баланса!")
        );

        if (account.getAccountType().compareTo(AccountType.C) == 0) {
            return balance.getCreditBalance().add(balance.getCreditDebt()).compareTo(valueTransfer) >= 0;
        } else {
            return balance.getDebitBalance().compareTo(valueTransfer) >= 0;
        }
    }
}
