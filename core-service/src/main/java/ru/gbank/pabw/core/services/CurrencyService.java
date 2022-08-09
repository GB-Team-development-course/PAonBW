package ru.gbank.pabw.core.services;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import ru.gbank.pabw.core.entities.Account;
import ru.gbank.pabw.core.repositories.account.AccountRepository;
import ru.gbank.pabw.model.dto.CurrencyDto;
import ru.gbank.pabw.model.enums.AccountType;
import ru.gbank.pabw.model.enums.Currency;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class CurrencyService {

    public List<CurrencyDto> getAll() {
        return Stream.of(Currency.values())
                .map(currency -> {
                    return CurrencyDto
                            .builder()
                            .currencyId(currency.getId())
                            .currencyCodeAndName(currency.getDenomination() +" "+currency.getDescription())
                            .build();
                })
                .collect(Collectors.toList());
    }

}
