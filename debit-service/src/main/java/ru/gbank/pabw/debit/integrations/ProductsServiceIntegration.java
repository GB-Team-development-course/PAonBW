package ru.gbank.pabw.debit.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.gbank.pabw.model.dto.ProductDto;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductsServiceIntegration {
    private final RestTemplate restTemplate;

    @Value("${integrations.core-service.url}")
    private String coreServiceUrl;

    public List<ProductDto> findAll() {
        return restTemplate.getForObject(coreServiceUrl + "/api/v1/product/", List.class);
    }
}
