package ru.gbank.pabw.core.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.gbank.pabw.model.enums.ResponseCode;
import ru.gbank.pabw.model.response.Response;
import ru.gbank.pabw.model.response.ResponseFactory;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Response> failedValidateOrderTransfer(ValidationProcessException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(ResponseFactory.errorResponse(ResponseCode.ACCESS_RIGHTS_VALIDATION_ERROR,e.getMessage()), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<Response> failedFoundAccountException(AccountNotFoundException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(ResponseFactory.errorResponse(ResponseCode.ACCOUNT_NOT_FOUND,e.getMessage()), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    public ResponseEntity<Response> failedCloseAccountException(CloseAccountException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(ResponseFactory.errorResponse(ResponseCode.ACCOUNT_CLOSED_ERROR,e.getMessage()), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<Response> failedBlockAccountException(BlockAccountException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(ResponseFactory.errorResponse(ResponseCode.ACCOUNT_BLOCK_ERROR,e.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
