package ru.gb.credit.controllers;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.credit.dto.AccountDto;
import ru.gb.credit.integrations.AccountsServiceIntegration;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/credit")
@AllArgsConstructor
public class CreditController {
	//    private final DebitOperationService debitOperationService;
	private final AccountsServiceIntegration accountsServiceIntegration;

	//todo пока этот контроллер для тестирования интеграции

	@GetMapping("/{currentDate}")
	public List<AccountDto> findByAccountNumber(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") final LocalDate currentDate) {
		return accountsServiceIntegration.findAllCreditByDate(currentDate);
	}

}
