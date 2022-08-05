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
 * Типы статусов начисления
 */
@Getter
@AllArgsConstructor
public enum AccrualStatus {

    IN_PROGRESS(0, "В исполнении"),
    REJECTED(1, "Отказ, ошибка на стороне клиента"),
    SUCCESS(2, "Успешно"),
    ERROR(3, "Ошибка на стороне сервиса");

    private static final Map<Integer, AccrualStatus> LOOKUP_BY_ID = Stream
            .of(AccrualStatus.values())
            .collect(Collectors.toMap(AccrualStatus::getId, Function.identity()));

    private final int id;

    private final String description;

    @NonNull
    public static AccrualStatus getById(final int accrualStatusId) {
        return LOOKUP_BY_ID.get(accrualStatusId);
    }

    @Converter(autoApply = true)
    public static class OrderStatusConverter implements AttributeConverter<AccrualStatus, Integer> {
        @Override
        @NonNull
        public Integer convertToDatabaseColumn(@NonNull final AccrualStatus accrualStatus) {
            return accrualStatus.id;
        }

        @Override
        @NonNull
        public AccrualStatus convertToEntityAttribute(@NonNull final Integer accrualStatusId) {
            return LOOKUP_BY_ID.get(accrualStatusId);
        }
    }


}
