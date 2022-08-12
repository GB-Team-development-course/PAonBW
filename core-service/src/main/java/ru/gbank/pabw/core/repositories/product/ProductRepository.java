package ru.gbank.pabw.core.repositories.product;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.gbank.pabw.core.entities.Product;
import ru.gbank.pabw.model.enums.ProductType;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	@Query("""
            SELECT p FROM Product p
            WHERE p.productStatus = ru.gbank.pabw.model.enums.ProductStatus.ACTIVE
            AND (p.productType = ?1)
            """)
	List<Product> findAllByType(@NonNull final ProductType productType);
}
