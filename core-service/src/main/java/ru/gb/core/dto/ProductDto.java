package ru.gb.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gb.core.enums.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private long id;
    private String name;
    private ProductType productType;
    private ProductStatus productStatus;
    private BigDecimal interestRatePercent;

}

