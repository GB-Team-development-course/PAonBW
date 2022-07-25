package ru.gb.core.converters;


import org.springframework.stereotype.Component;
import ru.gb.core.dto.ProductDto;
import ru.gb.core.entities.Product;

@Component
public class ProductConverter {


    public ProductDto entityToDto(Product product) {
        ProductDto out = new ProductDto();
        out.setId(product.getId());
        out.setName(product.getName());
        out.setProductStatus(product.getProductStatus());
        out.setInterestRatePercent(product.getInterestRatePercent());
        return out;
    }
}
