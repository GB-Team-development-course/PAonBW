package ru.gbank.pabw.model.enums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Типы статусов распоряжения
 */
@Getter
@AllArgsConstructor
public enum OrderStatus {

    IN_PROGRESS(0, "В исполнении"),
    REJECTED(1, "Отказ"), //Ошибка со строны Клиента
    SUCCESS(2, "Успешно"),
    ERROR(3, "Ошибка"); // Ошибка со стороны сервиса

    private static final Map<Integer, OrderStatus> LOOKUP_BY_ID = Stream
            .of(OrderStatus.values())
            .collect(Collectors.toMap(OrderStatus::getId, Function.identity()));

    private final int id;

    private final String description;

    @NonNull
    public static OrderStatus getById(final int orderStatusId) {
        return LOOKUP_BY_ID.get(orderStatusId);
    }

    @Converter(autoApply = true)
    public static class OrderStatusConverter implements AttributeConverter<OrderStatus, Integer> {
        @Override
        @NonNull
        public Integer convertToDatabaseColumn(@NonNull final OrderStatus orderStatus) {
            return orderStatus.id;
        }

        @Override
        @NonNull
        public OrderStatus convertToEntityAttribute(@NonNull final Integer orderStatusId) {
            return LOOKUP_BY_ID.get(orderStatusId);
        }
    }


}
