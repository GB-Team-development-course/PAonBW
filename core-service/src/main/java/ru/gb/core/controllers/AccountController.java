package ru.gb.core.controllers;

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
import ru.gb.core.dto.AccountDto;
import ru.gb.core.dto.CreateAccountRequest;
import ru.gb.core.enums.Currency;
import ru.gb.core.response.Response;
import ru.gb.core.services.AccountOperationService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/account")
@AllArgsConstructor
@Tag(name = "Счета", description = "Методы работы со счетами")
public class AccountController {
    private final AccountOperationService accountOperationService;

    @GetMapping("/activeDebit/{currentDate}")
    public List<AccountDto> findByDate(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") final LocalDate currentDate) {
        return accountOperationService.findAllDebitActiveByDate(currentDate);
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
    public Response<List<AccountDto>> findAll(@RequestHeader String username) {
        return accountOperationService.findAll(username);
    }

    @Operation(summary = "Запрос на создание кредитного счёта", responses = {@ApiResponse(description = "Успешный ответ", responseCode = "200")})
    @PostMapping("/credit")
    @ResponseStatus(HttpStatus.CREATED)
    public Response<AccountDto> createCreditAccount(@RequestHeader String username, @RequestBody CreateAccountRequest createAccountDto) {
        //todo добавить возможность выбора продукта
        return accountOperationService.createCreditAccount(
                username,
                Currency.getById(createAccountDto.getCurrency()),
                createAccountDto.getCredit(),
                createAccountDto.getProductId());
    }

    @Operation(summary = "Запрос на создание дебетового счёта", responses = {@ApiResponse(description = "Успешный ответ", responseCode = "200")})
    @PostMapping("/debit")
    @ResponseStatus(HttpStatus.CREATED)
    public Response<AccountDto> createDebitAccount(@RequestHeader String username, @RequestBody CreateAccountRequest createAccountDto) {
        //todo добавить возможность выбора продукта
        return accountOperationService.createDebitAccount(
                username,
                Currency.getById(createAccountDto.getCurrency()),
                createAccountDto.getProductId());
    }


    @PutMapping("/block/{accountNum}")
    @ResponseStatus(HttpStatus.OK)
    public Response<AccountDto> blockAccount(@RequestHeader String username, @PathVariable String accountNum) {
        return accountOperationService.blockAccount(username, accountNum);

    }

    @PutMapping("/close/{accountNum}")
    @ResponseStatus(HttpStatus.OK)
    public Response<AccountDto> closeAccount(@RequestHeader String username, @PathVariable String accountNum) {
        return accountOperationService.closeAccount(username, accountNum);
    }
}
