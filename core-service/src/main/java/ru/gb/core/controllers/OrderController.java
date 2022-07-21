package ru.gb.core.controllers;
/* 
10.07.2022: Alexey created this file inside the package: ru.gb.core.controllers 
*/

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.gb.core.dto.OrderDtoRequest;
import ru.gb.core.dto.OrderDtoResponse;
import ru.gb.core.entities.Order;
import ru.gb.core.response.Response;
import ru.gb.core.services.ValidationTransferService;

@RestController
@RequestMapping("/api/v1/order")
@AllArgsConstructor
@Tag(name = "Переводы", description = "Методы работы с переводами")
public class OrderController {

    private final ValidationTransferService validationTransferService;

    @Operation(summary = "Запрос на перевод средств",
            responses = {@ApiResponse(description = "Успешный ответ", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = OrderDtoResponse.class)))})
    @PostMapping(path = "")
    public Response<OrderDtoResponse> doTransfer(@RequestHeader("username") String username, @RequestBody OrderDtoRequest orderDtoRequest) {
        return validationTransferService.validateTransfer(username, orderDtoRequest);
    }

    //TODO получить все переводы по @RequestHeader("username")
}

