package ru.gbank.services;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.gbank.dto.AccountDto;
import ru.gbank.dto.BalanceDto;
import ru.gbank.dto.OrderDtoRequest;
import ru.gbank.dto.ProductDto;
import ru.gbank.entity.Accrual;
import ru.gbank.enums.AccrualStatus;
import ru.gbank.integrations.AccountsServiceIntegration;
import ru.gbank.integrations.BalancesServiceIntegration;
import ru.gbank.integrations.OrderIntegrationService;
import ru.gbank.integrations.ProductsServiceIntegration;
import ru.gbank.utils.RateCalculationUtils;

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
public class DebitOperationService {

   private final AccountsServiceIntegration accountsServiceIntegration;
   private final BalancesServiceIntegration balancesServiceIntegration;
   private final ProductsServiceIntegration productsServiceIntegration;
   private final AccrualService accrualService;
   private final OrderIntegrationService orderIntegrationService;

   public void calculateDailyAccruals(LocalDate currentDate) {

      ObjectMapper mapper = JsonMapper.builder()
              .addModule(new JavaTimeModule())
              .build();


      List<AccountDto> accounts = mapper.convertValue(accountsServiceIntegration
              .findAllDebitByDate(currentDate), new TypeReference<List<AccountDto>>() {});

      Map<Long, BalanceDto> balances = mapper
              .convertValue(balancesServiceIntegration.findAll(), new TypeReference<List<BalanceDto>>() {
              })
              .stream()
              .collect(Collectors.toMap(BalanceDto::getAccount, Function.identity()));

      Map<Long, ProductDto> products = mapper
              .convertValue(productsServiceIntegration.findAll(), new TypeReference<List<ProductDto>>() {
              })
              .stream()
              .collect(Collectors.toMap(ProductDto::getId, Function.identity()));

      List<Accrual> accruals = new ArrayList<>();

      accounts.forEach(account -> {

         BigDecimal amount = RateCalculationUtils.calculateRateForOneDay(
                 products.get(account.getProductId()).getInterestRatePercent(),
                 balances.get(account.getAccountId()).getDebitBalance());

         Accrual accrual = new Accrual(
                 null,
                 account.getAccountNumber(),
                 amount,
                 account.getCurrency(),
                 AccrualStatus.IN_PROGRESS,
                 LocalDateTime.now(),
                 null
         );
         log.info("Номер счёта на обработке " + account.getAccountNumber() + ". Сумма начисленного процента " + accrual.getAmount());
         accruals.add(accrual);
      });

      accrualService.saveAll(accruals);

      accruals.forEach(accrual -> {
         OrderDtoRequest orderDtoRequest = new OrderDtoRequest(
                 "T1001",
                 accrual.getTargetAccount(),
                 accrual.getAmount(),
                 accrual.getCurrency(),
                 "Начисление процентов по счёту"
         );
         //todo получить ответ и обновить accrual
         orderIntegrationService.sentOrderRequest(orderDtoRequest);
      });
      //todo завести технический счёт перевод с T->D
   }
}
