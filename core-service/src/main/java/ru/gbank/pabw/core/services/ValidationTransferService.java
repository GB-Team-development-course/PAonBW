package ru.gbank.pabw.core.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gbank.pabw.core.entities.Account;
import ru.gbank.pabw.core.entities.Balance;
import ru.gbank.pabw.core.entities.Order;
import ru.gbank.pabw.core.exceptions.ValidationProcessException;
import ru.gbank.pabw.model.dto.OrderDtoRequest;
import ru.gbank.pabw.model.enums.AccountStatus;
import ru.gbank.pabw.model.enums.AccountType;
import ru.gbank.pabw.model.enums.OrderStatus;

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

    public Order validateTransfer(String username, OrderDtoRequest orderDtoRequest) {

        List<String> errors = new ArrayList<>();
        Order order = new Order();

        validateAccountsAndBalances(orderDtoRequest, errors, order,username);

        if (!errors.isEmpty()) {
            throw new ValidationProcessException(errors);
        }

        order.setExternalOrderGuid(UUID.randomUUID());
        order.setExecutionStart(LocalDateTime.now());
        order.setCurrency(orderDtoRequest.getCurrency());
        order.setPaymentPurpose(orderDtoRequest.getPaymentPurpose());
        order.setOrderStatus(OrderStatus.IN_PROGRESS);
        return order;
    }

    private void validateAccountsAndBalances(OrderDtoRequest orderDtoRequest, List<String> errors, Order order,String username) {

        Optional<Account> sourceAccount = accountService.findByClientUsernameAndAccountNumber(username,orderDtoRequest.getSourceAccount());
        Optional<Account> targetAccount = accountService.findByAccountNumber(orderDtoRequest.getTargetAccount());

        if (sourceAccount.isEmpty()) {
            errors.add("Аккаунт " + orderDtoRequest.getSourceAccount() + " не найден или не принадлежит пользователю "+ username);
            return;
        }

        if (targetAccount.isEmpty()) {
            errors.add("Аккаунт " + orderDtoRequest.getTargetAccount() + " не найден!");
            return;
        }

        Optional<Balance> sourceBalance = balanceService.findByAccountId(sourceAccount.get().getId());
        Optional<Balance> targetBalance = balanceService.findByAccountId(targetAccount.get().getId());

        if (sourceBalance.isEmpty()) {
            errors.add("Отсутствует баланс у аккаунта отправителя");
            return;
        }
        if (targetBalance.isEmpty()) {
            errors.add("Отсутствует баланс у аккаунта получателя");
            return;
        }

        if (sourceAccount.get().getAccountStatus() == AccountStatus.CLOSED) {
            errors.add("Аккаунт " + sourceAccount.get().getAccountNumber() + " закрыт");
        }

        if (sourceAccount.get().getAccountStatus() == AccountStatus.BLOCKED) {
            errors.add("Аккаунт " + sourceAccount.get().getAccountNumber() + " заблокирован");
        }

        if (targetAccount.get().getAccountStatus() == AccountStatus.CLOSED) {
            errors.add("Аккаунт " + targetAccount.get().getAccountNumber() + " закрыт");
        }

        if (targetAccount.get().getAccountStatus() == AccountStatus.BLOCKED) {
            errors.add("Аккаунт " + targetAccount.get().getAccountNumber() + " заблокирован");
        }

        if (sourceAccount.get().getAccountNumber().equals(targetAccount.get().getAccountNumber())) {
            errors.add("Перевод доступен только на другой счет !");
        }

        if (sourceAccount.get().getCurrency()!=orderDtoRequest.getCurrency()){
            errors.add("Валюта счёта списания не соответствует валюте в переводе");
        }

        if (targetAccount.get().getCurrency()!=orderDtoRequest.getCurrency()){
            errors.add("Валюта счёта начисления не соответствует валюте в переводе");
        }

        if (sourceAccount.get().getAccountType() == AccountType.C
                && sourceBalance.get().getCreditBalance().compareTo(orderDtoRequest.getAmount()) < 0) {
            errors.add("Недостаточный баланс для перевода " + sourceBalance.get().getCreditBalance());
        }


        if ((sourceAccount.get().getAccountType() == AccountType.D ||
                sourceAccount.get().getAccountType() == AccountType.T) &&
                sourceBalance.get().getDebitBalance().compareTo(orderDtoRequest.getAmount()) < 0
        ) {
            errors.add("Недостаточный баланс для перевода " + sourceBalance.get().getDebitBalance());
        }

        order.setAmount(orderDtoRequest.getAmount());
        order.setSourceAccount(sourceAccount.get().getAccountNumber());
        order.setTargetAccount(targetAccount.get().getAccountNumber());
    }
}

