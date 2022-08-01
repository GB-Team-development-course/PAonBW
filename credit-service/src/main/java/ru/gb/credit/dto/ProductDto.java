package ru.gb.credit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

	private Long id;
	private String name;
	private String productType;
	private String productStatus;
	private BigDecimal interestRatePercent;

}
