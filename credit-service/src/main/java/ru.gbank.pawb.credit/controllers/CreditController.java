package ru.gbank.pawb.credit.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.gbank.pabw.model.dto.AccountDto;
import ru.gbank.pawb.credit.integrations.AccountsServiceIntegration;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/credit")
@AllArgsConstructor
@Tag(name = "Кредиты", description = "Методы работы с кредимтами")
public class CreditController {
	//    private final DebitOperationService debitOperationService;
	private final AccountsServiceIntegration accountsServiceIntegration;

	//todo пока этот контроллер для тестирования интеграции

	@GetMapping("/{currentDate}")
	@Operation(summary = "Запрос кредитных счетов для пользователя", responses = {@ApiResponse(description = "Успешный ответ", responseCode = "200", content = @Content(schema = @Schema(implementation = List.class)))})
	public List<AccountDto> findByAccountNumber(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") final LocalDate currentDate) {
		return accountsServiceIntegration.findAllCreditByDate(currentDate);
	}

}
