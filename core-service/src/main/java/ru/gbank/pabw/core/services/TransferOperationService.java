package ru.gbank.pabw.core.services;
/* 
11.07.2022: Alexey created this file inside the package: ru.gb.core.services 
*/

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gbank.pabw.core.entities.Account;
import ru.gbank.pabw.core.entities.Balance;
import ru.gbank.pabw.core.entities.Order;
import ru.gbank.pabw.model.enums.AccountType;
import ru.gbank.pabw.model.enums.OrderStatus;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class TransferOperationService {

    private final OrderService orderService;
    private final BalanceService balanceService;

    private final AccountService accountService;


    public Order doTransferByOrder(Order order) {

        doTransfer(order);

        order.setOrderStatus(OrderStatus.SUCCESS);
        order.setExecutionEnd(LocalDateTime.now());
        return orderService.save(order);
    }

    private void doTransfer(Order order) {

        Account sourceAccount = accountService.findByAccountNumber(order.getSourceAccount()).get();
        Account targetAccount = accountService.findByAccountNumber(order.getTargetAccount()).get();
        Balance sourceBalance = balanceService.findByAccountId(sourceAccount.getId()).get();
        Balance targetBalance = balanceService.findByAccountId(targetAccount.getId()).get();

        if (sourceAccount.getAccountType()==AccountType.C) {
            sourceBalance.setCreditBalance(sourceBalance.getCreditBalance().subtract(order.getAmount()));
        }

        if (sourceAccount.getAccountType() == AccountType.D ||
                sourceAccount.getAccountType() == AccountType.T) {
            sourceBalance.setDebitBalance(sourceBalance.getDebitBalance().subtract(order.getAmount()));
        }

        if (targetAccount.getAccountType() == AccountType.C){
            targetBalance.setCreditBalance(targetBalance.getCreditBalance().add(order.getAmount()));
        }

        if (targetAccount.getAccountType() == AccountType.D ||
                targetAccount.getAccountType() == AccountType.T){
            targetBalance.setDebitBalance(targetBalance.getDebitBalance().add(order.getAmount()));
        }

        balanceService.save(sourceBalance);
        balanceService.save(targetBalance);

    }
}
