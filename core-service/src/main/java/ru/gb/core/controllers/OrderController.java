package ru.gb.core.controllers;
/* 
10.07.2022: Alexey created this file inside the package: ru.gb.core.controllers 
*/

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.gb.core.dto.OrderDtoRequest;
import ru.gb.core.entities.Order;
import ru.gb.core.services.TransferOperationService;
import ru.gb.core.services.ValidationTransferService;

@RestController
@RequestMapping("/api/v1/order")
@AllArgsConstructor
@Tag(name = "Переводы", description = "Методы работы с переводами")
public class OrderController {

    private final ValidationTransferService validationTransferService;

    @PostMapping(path = "")
    public Order doTransfer(@RequestHeader Long clientId, @RequestBody OrderDtoRequest orderDtoRequest) {
        return validationTransferService.validateTransfer(orderDtoRequest);
    }

}

