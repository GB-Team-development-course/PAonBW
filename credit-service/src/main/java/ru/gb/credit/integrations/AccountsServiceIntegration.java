package ru.gb.credit.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import ru.gb.credit.dto.AccountDto;
import ru.gb.credit.exceptions.CoreServiceException;
import ru.gb.credit.exceptions.CreditServiceError;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AccountsServiceIntegration {
	private final WebClient webClient;

	public List<AccountDto> findAllCreditByDate(LocalDate date) {

		return webClient
				.get()
				.uri("/api/v1/account/activeCredit/")
				.retrieve()
				.onStatus(HttpStatus::is4xxClientError, clientResponse -> clientResponse.bodyToMono(CreditServiceError.class).map(body -> new CoreServiceException(body.getMessage())))
				.onStatus(HttpStatus::is5xxServerError, clientResponse -> clientResponse.bodyToMono(CreditServiceError.class).map(body -> new HttpServerErrorException(HttpStatus.GATEWAY_TIMEOUT, body.getMessage())))
				.bodyToMono(new ParameterizedTypeReference<List<AccountDto>>() {})
				.block();
	}
}
