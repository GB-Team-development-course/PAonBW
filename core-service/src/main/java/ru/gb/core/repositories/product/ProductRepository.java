package ru.gb.core.repositories.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.core.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
