package ru.gbank.pabw.core.repositories.order;/*
10.07.2022: Alexey created this file inside the package: ru.gb.core.services 
*/

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gbank.pabw.core.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
