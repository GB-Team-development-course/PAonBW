package ru.gb.core.services;
/* 
14.07.2022: Alexey created this file inside the package: ru.gb.core.services 
*/

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.core.dto.OrderDtoRequest;
import ru.gb.core.entities.Account;
import ru.gb.core.entities.Balance;
import ru.gb.core.entities.Order;
import ru.gb.core.enums.AccountType;
import ru.gb.core.enums.Currency;
import ru.gb.core.enums.OrderStatus;
import ru.gb.core.enums.TransferDirection;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ValidationTransferService {
    private final OrderService orderService;
    private final AccountService accountService;
    private final BalanceService balanceService;
    private final TransferOperationService transferOperationService;


    public Order validateTransfer(OrderDtoRequest orderDtoRequest) {
        Order order = new Order();
        order.setExternalOrderGuid(UUID.randomUUID());
        order.setExecutionStart(LocalDateTime.now());
        order.setCurrency(Currency.USD);
        order.setTransferDirection(TransferDirection.D_TO_D); //TODO findTransferDirection
        order.setPaymentPurpose("TEST");

        return doValidation(order, orderDtoRequest);
    }

    private Order doValidation(Order order, OrderDtoRequest orderDtoRequest) {
        List<String> validationErrors = new ArrayList<>();
        Optional<Account> account = accountService.findByAccountNumber(orderDtoRequest.getSourceAccount());

        //Валидация аккаунта отправителя
        if (account.isEmpty()) {
            validationErrors.add(String.format("SWW"));
            order.setAdditionalInformation(validationErrors.toString());
            order.setOrderStatus(OrderStatus.ERROR);
            return orderService.save(order);
        } else {
            order.setSourceAccount(account.get().getAccountNumber());
        }

        //Валидация наличия баланса
        if (!doValidationOfAccountBalanceByAccount(account.get(), orderDtoRequest.getAmount())) {
            validationErrors.add("SWW");
            order.setAdditionalInformation(validationErrors.toString());
            order.setOrderStatus(OrderStatus.ERROR);
            return orderService.save(order);
        } else {
            order.setAmount(orderDtoRequest.getAmount());
        }

        account = accountService.findByAccountNumber(orderDtoRequest.getTargetAccount());

        //Валидация аккаунта получателя
        if (account.isEmpty()) {
            validationErrors.add(String.format("SWW"));
            order.setAdditionalInformation(validationErrors.toString());
            order.setOrderStatus(OrderStatus.ERROR);
            return orderService.save(order);
        } else {
            order.setTargetAccount(account.get().getAccountNumber());
        }

        return transferOperationService.doTransferByOrder(order);
    }

    //Метод валидации баланса
    private Boolean doValidationOfAccountBalanceByAccount(Account account, BigDecimal valueTransfer) {
        Optional<Balance> balance = balanceService.findByAccountId(account.getId());
        return account.getAccountType().compareTo(AccountType.C) == 0 ?
                balance.get().getCreditBalance().add(balance.get().getCreditDebt()).compareTo(valueTransfer) > 0
                : balanceService.findByAccountId(account.getId()).get().getDebitBalance().compareTo(valueTransfer) > 0;
    }


}
