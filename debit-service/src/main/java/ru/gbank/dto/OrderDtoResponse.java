package ru.gbank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gbank.enums.Currency;
import ru.gbank.enums.OrderStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDtoResponse {
    private String sourceAccount;
    private String targetAccount;
    private String paymentPurpose;
    private Currency currency;
    private OrderStatus orderStatus;
    private LocalDateTime executionEnd;
}
