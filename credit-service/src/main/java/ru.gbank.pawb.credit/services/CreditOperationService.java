package ru.gbank.pawb.credit.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.gbank.pabw.model.dto.AccountDto;
import ru.gbank.pabw.model.dto.BalanceDto;
import ru.gbank.pabw.model.dto.OrderDtoRequest;
import ru.gbank.pabw.model.dto.ProductDto;
import ru.gbank.pabw.model.enums.InterestStatus;
import ru.gbank.pawb.credit.entity.Interest;
import ru.gbank.pawb.credit.integrations.AccountsServiceIntegration;
import ru.gbank.pawb.credit.integrations.BalancesServiceIntegration;
import ru.gbank.pawb.credit.integrations.OrderIntegrationService;
import ru.gbank.pawb.credit.integrations.ProductsServiceIntegration;
import ru.gbank.pawb.credit.utils.RateCalculationUtils;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class CreditOperationService {

	private final AccountsServiceIntegration accountsServiceIntegration;
	private final BalancesServiceIntegration balancesServiceIntegration;
	private final ProductsServiceIntegration productsServiceIntegration;
	private final InterestService interestService;
	private final OrderIntegrationService orderIntegrationService;
	private final String TECHNICAL_ACCOUNT_NUMBER = "T1001";

	public void calculateDailyInterests(LocalDate currentDate) {

		ObjectMapper mapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();

		List<AccountDto> accounts = mapper.convertValue(accountsServiceIntegration.findAllCreditByDate(currentDate),
				new TypeReference<List<AccountDto>>() {});

		Map<Long, BalanceDto> balances = mapper.convertValue(balancesServiceIntegration.findAll(),
				new TypeReference<List<BalanceDto>>() {}).stream().collect(Collectors.toMap(BalanceDto::getAccount, Function.identity()));

		Map<Long, ProductDto> products = mapper.convertValue(productsServiceIntegration.findAll(),
				new TypeReference<List<ProductDto>>() {}).stream().collect(Collectors.toMap(ProductDto::getId, Function.identity()));

		List<Interest> interests = new ArrayList<>();

		accounts.forEach(account -> {

			BigDecimal amount = RateCalculationUtils.calculateRateForOneDay(
					products.get(account.getProductId()).getInterestRatePercent(),
					balances.get(account.getAccountId()).getCreditDebt());

			Interest interest = new Interest(
					null,
					account.getUsername(),
					account.getAccountNumber(),
					amount,
					account.getCurrency(),
					InterestStatus.IN_PROGRESS,
					LocalDateTime.now(),
					null);

			log.info("Номер счёта на обработке " + account.getAccountNumber() + ". Сумма начисленного процента " + interest.getAmount());
			interests.add(interest);
		});

		interestService.saveAll(interests);

		interests.forEach(interest -> {
			OrderDtoRequest orderDtoRequest = new OrderDtoRequest(
					interest.getTargetAccount(),
					TECHNICAL_ACCOUNT_NUMBER,
					interest.getAmount(),
					interest.getCurrency(),
					"Списание процентов по кредиту");
			orderIntegrationService.sentOrderRequest(orderDtoRequest, interest.getUsername());
		});
	}
}
