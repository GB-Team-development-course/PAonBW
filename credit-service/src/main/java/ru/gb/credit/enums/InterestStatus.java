package ru.gb.credit.enums;

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
public enum InterestStatus {

	IN_PROGRESS(1, "В исполнении"), REJECTED(2, "Отказ, ошибка на стороне клиента"), SUCCESS(3, "Успешно"), ERROR(4, "Ошибка на стороне сервиса");

	private static final Map<Integer, InterestStatus> LOOKUP_BY_ID = Stream.of(InterestStatus.values()).collect(Collectors.toMap(InterestStatus::getId, Function.identity()));

	private final int id;

	private final String description;

	@NonNull
	public static InterestStatus getById(final int accrualStatusId) {
		return LOOKUP_BY_ID.get(accrualStatusId);
	}

	@Converter(autoApply = true)
	public static class OrderStatusConverter implements AttributeConverter<InterestStatus, Integer> {
		@Override
		@NonNull
		public Integer convertToDatabaseColumn(@NonNull final InterestStatus interestStatus) {
			return interestStatus.id;
		}

		@Override
		@NonNull
		public InterestStatus convertToEntityAttribute(@NonNull final Integer accrualStatusId) {
			return LOOKUP_BY_ID.get(accrualStatusId);
		}
	}

}
