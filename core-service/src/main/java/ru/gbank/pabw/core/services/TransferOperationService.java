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

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class TransferOperationService {

    private final OrderService orderService;
    private final BalanceService balanceService;

    @Transactional
    public Order doTransferByOrder(Account sourceAccount, Account targetAccount, Order order) {
        doTransferFromBalanceToTargetBalance(sourceAccount, targetAccount, order.getAmount());

        order.setOrderStatus(OrderStatus.SUCCESS);
        order.setExecutionEnd(LocalDateTime.now());
        return orderService.save(order);
    }

    private void doTransferFromBalanceToTargetBalance(Account accountSource, Account accountTarget, BigDecimal valueTransfer) {
        // get() потому что проверка сущности баланса была в сервисе валидации
        Balance balanceSource = balanceService.findByAccountId(accountSource.getId()).get();
        Balance balanceTarget = balanceService.findByAccountId(accountTarget.getId()).get();

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
