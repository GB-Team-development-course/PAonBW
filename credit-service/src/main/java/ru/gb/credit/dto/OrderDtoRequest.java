package ru.gb.credit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.gb.credit.enums.Currency;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class OrderDtoRequest {

	private String sourceAccount;
	private String targetAccount;
	private BigDecimal amount;
	private Currency currency;
	private String paymentPurpose;
}
