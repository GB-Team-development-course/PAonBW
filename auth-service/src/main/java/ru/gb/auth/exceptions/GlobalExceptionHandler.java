package ru.gb.auth.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<AuthError> userAlreadyExistsException(UserAlreadyExistException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new AuthError("USERNAME_ALREADY_EXISTS", e.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<AuthError> badCredentialException(BadCredentialsException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new AuthError("AUTH_SERVICE_INCORRECT_USERNAME_OR_PASSWORD", "Incorrect username or password"), HttpStatus.UNAUTHORIZED);
    }

}
