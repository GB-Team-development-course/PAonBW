package ru.gbank.pawb.credit.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import ru.gbank.pabw.model.dto.AccountDto;
import ru.gbank.pawb.credit.exceptions.CoreServiceException;
import ru.gbank.pawb.credit.exceptions.CreditServiceError;


import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AccountsServiceIntegration {
	private final WebClient webClient;

	public List<AccountDto> findAllCreditByDate(LocalDate date) {

		return webClient
				.get()
				.uri(uriBuilder -> uriBuilder
						.path("/api/v1/account/active")
						.queryParam("accountType","C")
						.queryParam("currentDate",date)
				.build())
				.retrieve()
				.onStatus(HttpStatus::is4xxClientError, clientResponse -> clientResponse.bodyToMono(CreditServiceError.class).map(body -> new CoreServiceException(body.getMessage())))
				.onStatus(HttpStatus::is5xxServerError, clientResponse -> clientResponse.bodyToMono(CreditServiceError.class).map(body -> new HttpServerErrorException(HttpStatus.GATEWAY_TIMEOUT, body.getMessage())))
				.bodyToMono(new ParameterizedTypeReference<List<AccountDto>>() {})
				.block();
	}
}
