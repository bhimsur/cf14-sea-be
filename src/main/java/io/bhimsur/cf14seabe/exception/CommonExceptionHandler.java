package io.bhimsur.cf14seabe.exception;

import io.bhimsur.cf14seabe.constant.ErrorCode;
import io.bhimsur.cf14seabe.dto.ErrorDetail;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@Slf4j
@NoArgsConstructor
public class CommonExceptionHandler {

    public ResponseEntity<Object> buildResponseEntity(Integer errorCode, String message) {
        return new ResponseEntity<>(ErrorDetail.builder()
                .errorCode(errorCode)
                .message(message)
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<Object> dataNotFoundException(DataNotFoundException e) {
        return buildResponseEntity(ErrorCode.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(DataAlreadyExistException.class)
    public ResponseEntity<Object> dataAlreadyExistException(DataAlreadyExistException e) {
        return buildResponseEntity(ErrorCode.ALREADY_EXIST, e.getMessage());
    }

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<Object> genericException(GenericException e) {
        return buildResponseEntity(ErrorCode.GENERIC, e.getMessage());
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<Object> insufficientBalanceException(InsufficientBalanceException e) {
        return buildResponseEntity(ErrorCode.BALANCE_ERROR, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> exception(Exception e) {
        return buildResponseEntity(ErrorCode.GENERAL, e.getMessage());
    }
}
