package ru.gbank.pabw.core.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.gbank.pabw.core.services.TransferExecutionService;
import ru.gbank.pabw.model.response.Response;
import ru.gbank.pabw.model.dto.TransferRequest;
import ru.gbank.pabw.model.dto.OrderDtoResponse;

@RestController
@RequestMapping("/api/v1/order")
@AllArgsConstructor
@Tag(name = "Переводы", description = "Методы работы с переводами")
public class OrderController {

    private final TransferExecutionService executionService;

    @Operation(summary = "Запрос на перевод средств",
            responses = {@ApiResponse(description = "Успешный ответ", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = OrderDtoResponse.class)))})
    @PostMapping(path = "")
    public Response<OrderDtoResponse> doTransfer(@RequestHeader("username") String username, @RequestBody TransferRequest transferRequest) {
        return executionService.executeTransfer(username, transferRequest);
    }
}

