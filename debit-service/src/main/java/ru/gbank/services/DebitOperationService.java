package ru.gbank.services;

import org.springframework.stereotype.Service;
import ru.gbank.dto.AccountDto;
import ru.gbank.integrations.AccountsServiceIntegration;

import java.time.LocalDate;
import java.util.List;

@Service
public class DebitOperationService {

   AccountsServiceIntegration accountsServiceIntegration;
   public void calculateDailyAccruals(LocalDate currentDate){
      List<AccountDto> accounts = accountsServiceIntegration.findAllDebitByDate(currentDate);

      //todo загрузить балансы
      //todo загрузить продукты
      //todo провести расчёт процентов
      //todo сформировать распоряжения
      //todo направить распоряжения в модуль переводов
      //todo вынести dto в общий модуль
      //todo добавить сущность продукт в core модуль
      //todo добавить сущность ордеров (или в той форме, в которой принимаем распоряжения в модуле переводов) в debit модуль

   }

}
