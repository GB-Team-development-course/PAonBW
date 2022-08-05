package ru.gbank.pabw.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gbank.pabw.model.enums.OrderStatus;
import ru.gbank.pabw.model.enums.Currency;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Модель перевода")
public class OrderDtoResponse {
    private String sourceAccount;
    private String targetAccount;
    private String paymentPurpose;
    private Currency currency;
    private OrderStatus orderStatus;
    private LocalDateTime executionEnd;
}
