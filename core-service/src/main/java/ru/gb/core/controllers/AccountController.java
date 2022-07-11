package ru.gb.core.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.gb.core.converters.AccountConverter;
import ru.gb.core.dto.AccountDto;
import ru.gb.core.dto.CreateAccountRequest;
import ru.gb.core.enums.Currency;
import ru.gb.core.services.AccountService;
import ru.gb.core.services.AccountOperationService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/account")
@AllArgsConstructor
@Tag(name = "Счета", description = "Методы работы со счетами")
public class AccountController {

    //todo необходимо разработать класс с ответами на запросы

    private final AccountService accountService;
    private final AccountConverter accountConverter;
    private final AccountOperationService accountOperationService;

    @Operation(summary = "Запрос на получение счёта по номеру", responses = {@ApiResponse(description = "Успешный ответ", responseCode = "200", content = @Content(schema = @Schema(implementation = AccountDto.class)))})
    @GetMapping("/{accountNum}")
    public AccountDto findByAccountNumber(@RequestHeader Long clientId,
                                          @PathVariable @Parameter(description = "Номер счёта", required = true) String accountNum) {
        //todo дописать exceptions, пока тут будет заглушка
        return accountConverter.entityToDto(accountService
                .findByClientIdAndAccountNumber(clientId, accountNum)
                .orElseThrow(() -> new RuntimeException("Счёт не найден")));
    }

    @Operation(summary = "Запрос на получение всех счетов", responses = {@ApiResponse(description = "Успешный ответ", responseCode = "200", content = @Content(schema = @Schema(implementation = List.class)))})
    @GetMapping("/")
    public List<AccountDto> findAll(@RequestHeader Long clientId) {
        return accountService.findAll(clientId)
                .stream()
                .map(accountConverter::entityToDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Запрос на создание кредитного счёта", responses = {@ApiResponse(description = "Успешный ответ", responseCode = "200")})
    @PostMapping("/createCredit")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDto createCreditAccount(@RequestHeader Long clientId, @RequestBody CreateAccountRequest createCreditAccountDto) {
        return accountConverter.entityToDto(
                accountOperationService
                        .createCreditAccount(
                                clientId,
                                Currency.getById(createCreditAccountDto.getCurrency()),
                                createCreditAccountDto.getCredit()));
    }

    @Operation(summary = "Запрос на создание дебетового счёта", responses = {@ApiResponse(description = "Успешный ответ", responseCode = "200")})
    @PostMapping("/createDebit")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDto createDebitAccount(@RequestHeader Long clientId, @RequestBody CreateAccountRequest createDebitAccountDto) {
        return accountConverter.entityToDto(
                accountOperationService.createDebitAccount(
                        clientId,
                        Currency.getById(createDebitAccountDto.getCurrency())));
    }


    @PutMapping("/blockAccount/{accountNum}")
    @ResponseStatus(HttpStatus.OK)
    public AccountDto blockAccount(@RequestHeader Long clientId, @PathVariable String accountNum) {
        return accountConverter.entityToDto(accountOperationService
                .blockAccount(clientId, accountNum)
                //todo дописать exceptions, пока тут будет заглушка
                .orElseThrow(() -> new RuntimeException("Счёт невозможно заблокировать")));
    }

    @PutMapping("/closeAccount/{accountNum}")
    @ResponseStatus(HttpStatus.OK)
    public AccountDto closeAccount(@RequestHeader Long clientId, @PathVariable String accountNum) {
        return accountConverter.entityToDto(accountOperationService
                .closeAccount(clientId, accountNum)
                //todo дописать exceptions, пока тут будет заглушка
                .orElseThrow(() -> new RuntimeException("Счёт невозможно закрыть")));
    }
}
