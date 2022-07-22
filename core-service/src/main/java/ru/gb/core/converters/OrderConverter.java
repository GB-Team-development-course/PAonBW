package ru.gb.core.converters;

import org.springframework.stereotype.Component;
import ru.gb.core.dto.OrderDtoResponse;
import ru.gb.core.entities.Order;

@Component
public class OrderConverter {
    public Order dtoToEntity(OrderDtoResponse orderDtoResponse) {
        throw new UnsupportedOperationException();
    }

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
