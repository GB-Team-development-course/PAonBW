package ru.gbank.pabw.core.converters;

import org.springframework.stereotype.Component;
import ru.gbank.pabw.core.entities.Order;
import ru.gbank.pabw.model.dto.OrderDtoResponse;

@Component
public class OrderConverter {

    public OrderDtoResponse entityToDto(Order order) {
        OrderDtoResponse out = new OrderDtoResponse();
        out.setSourceAccount(order.getSourceAccount());
        out.setTargetAccount(order.getTargetAccount());
        out.setCurrency(order.getCurrency());
        out.setOrderStatus(order.getOrderStatus());
        out.setExecutionEnd(order.getExecutionEnd());
        out.setPaymentPurpose(order.getPaymentPurpose());
        return out;
    }
}
