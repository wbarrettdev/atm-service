package com.william.atm.exception;

import com.william.atm.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
@SuppressWarnings("Duplicates")
public class RestApiErrorHandler {

    private static final String INTERNAL_ERROR_MESSAGE = "An unknown error occurred.";
    private static final String NUMBER_FORMAT_MESSAGE = "Failed parsing values in request. Please ensure details are correct";

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ErrorResponse response = new ErrorResponse();
        response.setErrorMessage(INTERNAL_ERROR_MESSAGE);
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setHasErrors(true);
        log.debug(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAccountNotFoundException(Exception ex) {
        ErrorResponse response = new ErrorResponse();
        response.setErrorMessage(ex.getMessage());
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setHasErrors(true);
        log.debug(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidPinException.class)
    public ResponseEntity<ErrorResponse> handleInvalidPinException(Exception ex) {
        ErrorResponse response = new ErrorResponse();
        response.setErrorMessage(ex.getMessage());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setHasErrors(true);
        log.debug(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<ErrorResponse> handleNumberFormatException(Exception ex) {
        ErrorResponse response = new ErrorResponse();
        response.setErrorMessage(NUMBER_FORMAT_MESSAGE);
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setHasErrors(true);
        log.debug(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
