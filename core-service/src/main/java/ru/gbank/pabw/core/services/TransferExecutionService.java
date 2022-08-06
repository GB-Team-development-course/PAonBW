package ru.gbank.pabw.core.services;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gbank.pabw.core.converters.OrderConverter;
import ru.gbank.pabw.core.entities.Order;
import ru.gbank.pabw.model.dto.OrderDtoRequest;
import ru.gbank.pabw.model.dto.OrderDtoResponse;
import ru.gbank.pabw.model.enums.ResponseCode;
import ru.gbank.pabw.model.response.Response;
import ru.gbank.pabw.model.response.ResponseFactory;

@Service
@AllArgsConstructor
public class TransferExecutionService {

    private final ValidationTransferService validationService;
    private final TransferOperationService operationService;
    private final OrderConverter orderConverter;

    @Transactional
    public Response<OrderDtoResponse> executeTransfer(String username, OrderDtoRequest orderDtoRequest) {

        Order order = validationService.validateTransfer(username, orderDtoRequest);
        operationService.doTransferByOrder(order);

        return ResponseFactory.successResponse(
                ResponseCode.ACCOUNT_OPERATION_COMPLETE,
                orderConverter.entityToDto(order)
        );
    }
}
