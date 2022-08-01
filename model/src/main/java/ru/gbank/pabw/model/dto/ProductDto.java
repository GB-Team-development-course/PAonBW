package ru.gbank.pabw.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gbank.pabw.model.enums.ProductStatus;
import ru.gbank.pabw.model.enums.ProductType;

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

