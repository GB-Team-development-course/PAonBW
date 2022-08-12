package ru.gbank.pabw.core.converters;

import org.springframework.stereotype.Component;
import ru.gbank.pabw.core.entities.Order;
import ru.gbank.pabw.model.dto.TransferRequest;
import ru.gbank.pabw.model.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class TransferRequestConverter {

    public Order toOrder(TransferRequest transferRequest) {
        Order order = new Order();
        order.setExternalOrderGuid(UUID.randomUUID());
        order.setSourceAccount(transferRequest.getSourceAccount());
        order.setTargetAccount(transferRequest.getTargetAccount());
        order.setAmount(transferRequest.getAmount());
        order.setCurrency(transferRequest.getCurrency());
        order.setPaymentPurpose(transferRequest.getPaymentPurpose());
        order.setOrderStatus(OrderStatus.IN_PROGRESS);
        order.setExecutionStart(LocalDateTime.now());
        return order;
    }
}
