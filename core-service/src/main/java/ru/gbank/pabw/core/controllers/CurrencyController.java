package ru.gbank.pabw.core.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.gbank.pabw.core.services.AccountOperationService;
import ru.gbank.pabw.core.services.CurrencyService;
import ru.gbank.pabw.model.dto.AccountDto;
import ru.gbank.pabw.model.dto.CreateAccountRequest;
import ru.gbank.pabw.model.dto.CurrencyDto;
import ru.gbank.pabw.model.response.Response;

import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

@RestController
@RequestMapping("/api/v1/currency")
@AllArgsConstructor
@Tag(name = "Счета", description = "Методы работы со счетами")
public class CurrencyController {
    private final CurrencyService currencyService;

    @GetMapping()
    @Operation(summary = "Запрос на получение списка валюты", responses = {@ApiResponse(description = "Успешный ответ", responseCode = "200", content = @Content(schema = @Schema(implementation = List.class)))})
    public List<CurrencyDto> getAll() {
        return currencyService.getAll();
    }

}
