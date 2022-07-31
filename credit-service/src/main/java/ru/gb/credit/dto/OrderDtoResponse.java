package ru.gb.credit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gb.credit.enums.Currency;
import ru.gb.credit.enums.OrderStatus;

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
