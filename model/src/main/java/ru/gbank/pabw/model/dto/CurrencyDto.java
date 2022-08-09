package ru.gbank.pabw.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gbank.pabw.model.enums.AccountStatus;
import ru.gbank.pabw.model.enums.AccountType;
import ru.gbank.pabw.model.enums.Currency;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Модель валюты")
public class CurrencyDto {

    @Schema(description = "id валюты")
    private Integer currencyId;
    @Schema(description = "Код и имя валюты")
    private String currencyCodeAndName;


}
