package ru.gbank.pabw.debit.services;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.gbank.pabw.debit.entity.Accrual;
import ru.gbank.pabw.debit.integrations.AccountsServiceIntegration;
import ru.gbank.pabw.debit.integrations.BalancesServiceIntegration;
import ru.gbank.pabw.debit.integrations.OrderIntegrationService;
import ru.gbank.pabw.debit.integrations.ProductsServiceIntegration;
import ru.gbank.pabw.debit.utils.RateCalculationUtils;
import ru.gbank.pabw.model.dto.AccountDto;
import ru.gbank.pabw.model.dto.BalanceDto;
import ru.gbank.pabw.model.dto.OrderDtoRequest;
import ru.gbank.pabw.model.dto.ProductDto;
import ru.gbank.pabw.model.enums.AccrualStatus;

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
                 account.getUsername(),
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
         orderIntegrationService.sentOrderRequest(orderDtoRequest,accrual.getUsername());
      });
   }
}
