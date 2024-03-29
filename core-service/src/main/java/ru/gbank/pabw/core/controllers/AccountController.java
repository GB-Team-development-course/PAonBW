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
import ru.gbank.pabw.model.dto.AccountDto;
import ru.gbank.pabw.model.dto.AccountWithBalanceDto;
import ru.gbank.pabw.model.dto.CreateAccountRequest;
import ru.gbank.pabw.model.enums.AccountType;
import ru.gbank.pabw.model.response.Response;
import ru.gbank.pabw.core.services.AccountOperationService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/account")
@AllArgsConstructor
@Tag(name = "Счета", description = "Методы работы со счетами")
public class AccountController {
    private final AccountOperationService accountOperationService;

    @GetMapping("/active")
    @Operation(summary = "Запрос на получение счетов по типам", responses = {@ApiResponse(description = "Успешный ответ", responseCode = "200", content = @Content(schema = @Schema(implementation = List.class)))})
    public List<AccountDto> findByDate(@RequestParam AccountType accountType,@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate currentDate) {
        return accountOperationService.findAllActiveByDate(accountType,currentDate);
    }

    @Operation(summary = "Запрос на получение счёта по номеру", responses = {@ApiResponse(description = "Успешный ответ", responseCode = "200", content = @Content(schema = @Schema(implementation = AccountDto.class)))})
    @GetMapping("/{accountNum}")
    public Response<AccountDto> findByAccountNumber(@RequestHeader String username,
                                          @PathVariable @Parameter(description = "Номер счёта", required = true)
                                          String accountNum) {
        return  accountOperationService.findByClientUsernameAndAccountNumber(username, accountNum);
    }

    @Operation(summary = "Запрос на получение всех счетов", responses = {@ApiResponse(description = "Успешный ответ", responseCode = "200", content = @Content(schema = @Schema(implementation = List.class)))})
    @GetMapping("/")
    public Response<List<AccountWithBalanceDto>> findAll(@RequestHeader String username) {
        return accountOperationService.findAll(username);
    }

    @Operation(summary = "Запрос на создание счёта", responses = {@ApiResponse(description = "Успешный ответ", responseCode = "200")})
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Response<AccountDto> createAccount(@RequestHeader String username, @RequestBody CreateAccountRequest createAccountRequest) {
        return accountOperationService.createAccount(
                username,createAccountRequest);
    }

    @PutMapping("/block/{accountNum}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Запрос на блокировку счета", responses = {@ApiResponse(description = "Успешный ответ", responseCode = "200", content = @Content(schema = @Schema(implementation = AccountDto.class)))})
    public Response<AccountDto> blockAccount(@RequestHeader String username, @PathVariable String accountNum) {
        return accountOperationService.blockAccount(username, accountNum);

    }

    @PutMapping("/close/{accountNum}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Запрос на закрытие счета", responses = {@ApiResponse(description = "Успешный ответ", responseCode = "200", content = @Content(schema = @Schema(implementation = AccountDto.class)))})
    public Response<AccountDto> closeAccount(@RequestHeader String username, @PathVariable String accountNum) {
        return accountOperationService.closeAccount(username, accountNum);
    }
}
