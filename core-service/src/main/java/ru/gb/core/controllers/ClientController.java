package ru.gb.core.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.gb.core.converters.AccountConverter;
import ru.gb.core.dto.AccountDto;
import ru.gb.core.dto.ClientDto;
import ru.gb.core.dto.CreateAccountRequest;
import ru.gb.core.enums.Currency;
import ru.gb.core.exceptions.FailedCreateClientException;
import ru.gb.core.services.AccountOperationService;
import ru.gb.core.services.AccountService;
import ru.gb.core.services.ClientService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/client")
@AllArgsConstructor
@Tag(name = "Клиенты", description = "Методы работы с клиентами")
public class ClientController {

    private final ClientService clientService;

    @Operation(summary = "Запрос на создание кредитного счёта", responses = {@ApiResponse(description = "Успешный ответ", responseCode = "200")})
    @PostMapping("/")
    public ClientDto createNewClient(@RequestHeader("username") String username) {
        return Optional.ofNullable(clientService.createNewClient(username))
                .map(client -> new ClientDto(client.getUsername()))
                .orElseThrow(() -> new FailedCreateClientException("Ошибка создания нового клиента"));
    }

    @Operation(summary = "Запрос на создание дебетового счёта", responses = {@ApiResponse(description = "Успешный ответ", responseCode = "200")})
    @DeleteMapping("/")
    public void deleteByUsername(@RequestHeader("username") String username) {
        clientService.deleteByUsername(username);
    }

}
