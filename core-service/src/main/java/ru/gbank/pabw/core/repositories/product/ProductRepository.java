package ru.gbank.pabw.core.repositories.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gbank.pabw.core.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
