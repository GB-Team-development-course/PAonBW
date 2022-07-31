package ru.gbank.pabw.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Типы продукта
 */
@Getter
@AllArgsConstructor
public enum ProductStatus {

    ACTIVE(1, "Активный"),
    CLOSED(2, "Закрытый");

    private final static Map<Integer, ProductStatus> LOOKUP_BY_ID = Stream
            .of(ProductStatus.values())
            .collect(Collectors.toMap(ProductStatus::getId, Function.identity()));

    private final int id;

    private final String description;

    @NonNull
    public static ProductStatus getById(final int productStatusId) {
        return LOOKUP_BY_ID.get(productStatusId);
    }
}
