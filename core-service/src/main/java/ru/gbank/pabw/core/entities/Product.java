package ru.gbank.pabw.core.entities;

import lombok.*;
import org.springframework.lang.Nullable;
import ru.gbank.pabw.model.enums.ProductStatus;
import ru.gbank.pabw.model.enums.ProductType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    /**
     * Уникальный идентификатор продукта на уровне системы
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_gen")
    @SequenceGenerator(name = "product_gen", sequenceName = "products_id_seq", allocationSize = 1)
    private Long id;

    /**
     * Наименование продукта
     */
    @NonNull
    @Column(name = "name")
    private String name;

    /**
     * Тип продукта
     */
    @NonNull
    @Column(name = "product_type_id")
    private ProductType productType;

    /**
     * Статус продукта
     */
    @NonNull
    @Column(name = "product_status_id")
    private ProductStatus productStatus;

    /**
     * Процентная ставка
     */
    @NonNull
    @Column(name = "int_rate")
    private BigDecimal interestRatePercent;

    /**
     * Дата и время создания
     */
    @NonNull
    @Column(name = "dt_created")
    private final LocalDateTime dtCreated = LocalDateTime.now();

    /**
     * Дата и время закрытия
     */
    @Setter
    @Nullable
    @Column(name = "dt_closed")
    private LocalDateTime dtClosed;

    /**
     * Дата и время последнего обновления
     */
    @Setter
    @Nullable
    @Column(name = "dt_last_update")
    private LocalDateTime dtLastUpdate;

}
