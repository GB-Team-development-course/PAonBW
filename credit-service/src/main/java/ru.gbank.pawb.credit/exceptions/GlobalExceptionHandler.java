package ru.gbank.pawb.credit.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<CreditServiceError> userAlreadyExistsException(CoreServiceException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new CreditServiceError("CORE_SERVICE_EXCEPTION", e.getMessage()), HttpStatus.BAD_GATEWAY);
    }
}
