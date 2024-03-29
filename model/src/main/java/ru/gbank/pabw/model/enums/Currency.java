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

@Getter
@AllArgsConstructor
public enum Currency {

    USD(0, 840, "Доллар США","T1001"),
    RUB(1, 810, "Российский рубль","T1002"),
    EUR(2, 978, "Евро","T1003"),
    CNY(3,156, "Китайский юань","T1004");

    private final static Map<Integer, Currency> LOOKUP_BY_ID = Stream
            .of(Currency.values())
            .collect(Collectors.toMap(Currency::getId, Function.identity()));

    private final int id;

    private final int denomination;

    private final String description;

    private final String technicalAccountNumber;

    @NonNull
    public static Currency getById(final int currencyId) {
        return LOOKUP_BY_ID.get(currencyId);
    }

    @Converter(autoApply = true)
    public static class CurrencyConverter implements AttributeConverter<Currency, Integer> {
        @Override
        @NonNull
        public Integer convertToDatabaseColumn(@NonNull final Currency currency) {
            return currency.id;
        }

        @Override
        @NonNull
        public Currency convertToEntityAttribute(@NonNull final Integer currencyId) {
            return LOOKUP_BY_ID.get(currencyId);
        }
    }
}
