package ru.gbank.controllers;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.gbank.dto.AccountDto;
import ru.gbank.integrations.AccountsServiceIntegration;
import ru.gbank.services.DebitOperationService;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api/v1/debit")
@AllArgsConstructor
public class DebitController {
//    private final DebitOperationService debitOperationService;
    private final AccountsServiceIntegration accountsServiceIntegration;


    //todo пока этот контроллер для тестирования интеграции

    @GetMapping("/{currentDate}")
    public List<AccountDto> findByAccountNumber(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") final LocalDate currentDate) {
        return  accountsServiceIntegration.findAllDebitByDate(currentDate);
    }

}
