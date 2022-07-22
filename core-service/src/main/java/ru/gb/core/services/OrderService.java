package ru.gb.core.services;
/* 
10.07.2022: Alexey created this file inside the package: ru.gb.core.services 
*/

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.core.entities.Order;
import ru.gb.core.repositories.order.OrderRepository;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public Order save(Order order) {
        return orderRepository.save(order);
    }
}
