package ru.gbank.pabw.core.converters;


import org.springframework.stereotype.Component;
import ru.gbank.pabw.core.entities.Product;
import ru.gbank.pabw.model.dto.ProductDto;

@Component
public class ProductConverter {


    public ProductDto entityToDto(Product product) {
        ProductDto out = new ProductDto();
        out.setId(product.getId());
        out.setName(product.getName());
        out.setProductType(product.getProductType());
        out.setProductStatus(product.getProductStatus());
        out.setInterestRatePercent(product.getInterestRatePercent());
        return out;
    }
}
