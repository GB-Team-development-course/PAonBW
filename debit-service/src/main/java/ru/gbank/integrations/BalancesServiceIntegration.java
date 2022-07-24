//package ru.gbank.integrations;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.Optional;
//
//@Component
//@RequiredArgsConstructor
//public class BalancesServiceIntegration {
//    private final RestTemplate restTemplate;
//
//    @Value("${integrations.core-service.url}")
//    private String productServiceUrl;
//
//    public Optional<BalanceDto> findById(Long id) {
//        BalanceDto productDto = restTemplate.getForObject(productServiceUrl + "/api/v1/products/" + id, BalanceDto.class);
//        return Optional.ofNullable(productDto);
//    }
//}
