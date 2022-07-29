package ru.gbank.integrations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.gbank.dto.OrderDtoRequest;


@Component
@RequiredArgsConstructor
@Slf4j
public class OrderIntegrationService {

    private final RestTemplate restTemplate;

    @Value("${integrations.core-service.url}")
    private String coreServiceUrl;

    public String sentOrderRequest(OrderDtoRequest orderDtoRequest,String username) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("username",username);

        HttpEntity request = new HttpEntity(orderDtoRequest, headers);
        String response = restTemplate.postForObject( coreServiceUrl + "/api/v1/order/", request , String.class );
        log.info(response);
        return  response;
    }
}
