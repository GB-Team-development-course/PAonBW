package ru.gbank.integrations;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.gbank.dto.OrderDtoRequest;
import ru.gbank.dto.OrderDtoResponse;
import ru.gbank.responce.Response;


@Component
@RequiredArgsConstructor
public class OrderIntegrationService {

    private final RestTemplate restTemplate;

    @Value("${integrations.core-service.url}")
    private String coreServiceUrl;

    public Response<OrderDtoResponse> sentOrderRequest(OrderDtoRequest orderDtoRequest) {
        //todo запихнуть сюда ордер
        return restTemplate.getForObject(coreServiceUrl + "/api/v1/order/", Response.class);
    }
}
