package ru.gbank.pawb.credit.integrations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.gbank.pabw.model.dto.TransferRequest;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderIntegrationService {

	private final WebClient webClient;

	public String sentOrderRequest(TransferRequest transferRequest, String username) {

		return webClient
                .post()
                .uri("/api/v1/order/")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header("username", username)
                .body(Mono.just(transferRequest), TransferRequest.class)
                .retrieve()
                .bodyToMono(String.class)
                .block();
	}
}
