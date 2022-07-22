package ru.gb.core.services;
/* 
11.07.2022: Alexey created this file inside the package: ru.gb.core.services 
*/

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.core.entities.Account;
import ru.gb.core.entities.Balance;
import ru.gb.core.entities.Order;
import ru.gb.core.enums.AccountType;
import ru.gb.core.enums.OrderStatus;
import ru.gb.core.exceptions.ValidationProcessException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class TransferOperationService {

    private final OrderService orderService;
    private final AccountService accountService;
    private final BalanceService balanceService;

    public Order doTransferByOrder(Account sourceAccount, Account targetAccount, Order order) {
        order.setSourceAccount(sourceAccount.getAccountNumber());
        order.setTargetAccount(targetAccount.getAccountNumber());

        doTransferFromBalanceToTargetBalance(sourceAccount, targetAccount, order.getAmount());

        order.setOrderStatus(OrderStatus.SUCCESS);
        order.setExecutionEnd(LocalDateTime.now());
        return orderService.save(order);
    }

    private void doTransferFromBalanceToTargetBalance(Account accountSource, Account accountTarget, BigDecimal valueTransfer) {
        Balance balanceSource = balanceService.findByAccountId(accountSource.getId())
                .orElseThrow(() -> new ValidationProcessException("Баланс не найден"));
        Balance balanceTarget = balanceService.findByAccountId(accountTarget.getId())
                .orElseThrow(() -> new ValidationProcessException("Баланс не найден"));

        if (accountSource.getAccountType().compareTo(AccountType.C) == 0) {
            balanceSource.setCreditBalance(balanceSource.getCreditBalance().subtract(valueTransfer));
        } else {
            balanceSource.setDebitBalance(balanceSource.getDebitBalance().subtract(valueTransfer));
        }

        if (accountTarget.getAccountType().compareTo(AccountType.C) == 0) {
            balanceTarget.setCreditBalance(balanceTarget.getCreditBalance().add(valueTransfer));
        } else {
            balanceTarget.setCreditBalance(balanceTarget.getDebitBalance().add(valueTransfer));
        }
        balanceService.save(balanceSource);
        balanceService.save(balanceTarget);
    }
}
