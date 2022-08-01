package ru.gb.credit.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import ru.gb.credit.dto.ProductDto;
import ru.gb.credit.exceptions.CoreServiceException;
import ru.gb.credit.exceptions.CreditServiceError;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductsServiceIntegration {
	private final WebClient webClient;

	public List<ProductDto> findAll() {

		return webClient
                .get()
                .uri("/api/v1/product/")
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> clientResponse.bodyToMono(CreditServiceError.class).map(body -> new CoreServiceException(body.getMessage())))
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> clientResponse.bodyToMono(CreditServiceError.class).map(body -> new HttpServerErrorException(HttpStatus.GATEWAY_TIMEOUT, body.getMessage())))
                .bodyToMono(new ParameterizedTypeReference<List<ProductDto>>() {})
                .block();

	}
}
