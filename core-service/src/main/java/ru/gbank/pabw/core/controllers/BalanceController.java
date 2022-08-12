package ru.gbank.pabw.core.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gbank.pabw.core.converters.BalanceConverter;
import ru.gbank.pabw.core.services.BalanceService;
import ru.gbank.pabw.model.dto.BalanceDto;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/balance")
@AllArgsConstructor
@Tag(name = "Балансы", description = "Методы работы с балансами")
public class BalanceController {
    private final BalanceService balanceService;
    private final BalanceConverter balanceConverter;

    @GetMapping("")
    @Operation(summary = "Запрос получения балансов для всех счетов", responses = {@ApiResponse(description = "Успешный ответ", responseCode = "200", content = @Content(schema = @Schema(implementation = List.class)))})
    public List<BalanceDto> findAll() {
        return balanceService.findAll().stream()
                .map(balanceConverter::entityToDto)
                .collect(Collectors.toList());
    }
}
