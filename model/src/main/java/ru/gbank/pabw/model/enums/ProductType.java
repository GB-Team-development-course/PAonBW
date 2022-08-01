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
public enum ProductType {

    CREDIT(0, "Кредитный"),
    DEPOSIT(1, "Депозитный");

    private final static Map<Integer, ProductType> LOOKUP_BY_ID = Stream
            .of(ProductType.values())
            .collect(Collectors.toMap(ProductType::getId, Function.identity()));

    private final int id;

    private final String description;

    @NonNull
    public static ProductType getById(final int productTypeId) {
        return LOOKUP_BY_ID.get(productTypeId);
    }
}
