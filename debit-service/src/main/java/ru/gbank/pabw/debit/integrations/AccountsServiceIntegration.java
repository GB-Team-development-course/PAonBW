package ru.gbank.pabw.debit.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.gbank.pabw.model.dto.AccountDto;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AccountsServiceIntegration {
    private final RestTemplate restTemplate;

    @Value("${integrations.core-service.url}")
    private String productServiceUrl;

    public List<AccountDto> findAllDebitByDate(LocalDate date) {
        return restTemplate.getForObject(productServiceUrl + "/api/v1/account/activeDebit/" + date, List.class);
    }
}
