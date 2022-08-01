package ru.gb.credit.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.gb.credit.dto.AccountDto;
import ru.gb.credit.dto.BalanceDto;
import ru.gb.credit.dto.OrderDtoRequest;
import ru.gb.credit.dto.ProductDto;
import ru.gb.credit.entity.Interest;
import ru.gb.credit.enums.InterestStatus;
import ru.gb.credit.integrations.AccountsServiceIntegration;
import ru.gb.credit.integrations.BalancesServiceIntegration;
import ru.gb.credit.integrations.OrderIntegrationService;
import ru.gb.credit.integrations.ProductsServiceIntegration;
import ru.gb.credit.utils.RateCalculationUtils;

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

	public void calculateDailyInterests(LocalDate currentDate) {

		ObjectMapper mapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();

		List<AccountDto> accounts = mapper.convertValue(accountsServiceIntegration.findAllCreditByDate(currentDate), new TypeReference<List<AccountDto>>() {
		});

		Map<Long, BalanceDto> balances = mapper.convertValue(balancesServiceIntegration.findAll(), new TypeReference<List<BalanceDto>>() {
		}).stream().collect(Collectors.toMap(BalanceDto::getAccount, Function.identity()));

		Map<Long, ProductDto> products = mapper.convertValue(productsServiceIntegration.findAll(), new TypeReference<List<ProductDto>>() {
		}).stream().collect(Collectors.toMap(ProductDto::getId, Function.identity()));

		List<Interest> interests = new ArrayList<>();

		accounts.forEach(account -> {

			BigDecimal amount = RateCalculationUtils.calculateRateForOneDay(products.get(account.getProductId()).getInterestRatePercent(), balances.get(account.getAccountId()).getCreditBalance());

			Interest interest = new Interest(null, account.getUsername(), account.getAccountNumber(), amount, account.getCurrency(), InterestStatus.IN_PROGRESS, LocalDateTime.now(), null);
			log.info("Номер счёта на обработке " + account.getAccountNumber() + ". Сумма начисленного процента " + interest.getAmount());
			interests.add(interest);
		});

		interestService.saveAll(interests);

		interests.forEach(interest -> {
			OrderDtoRequest orderDtoRequest = new OrderDtoRequest("T1001", interest.getTargetAccount(), interest.getAmount(), interest.getCurrency(), "Начисление процентов по счёту");
			orderIntegrationService.sentOrderRequest(orderDtoRequest, interest.getUsername());
		});
	}
}
