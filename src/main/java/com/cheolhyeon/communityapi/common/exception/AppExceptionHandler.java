package com.cheolhyeon.communityapi.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class AppExceptionHandler {
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(HttpRequestMethodNotSupportedException ex) {
        StringBuilder errorMessage = new StringBuilder();
        ProblemDetail body = ex.getBody();
        errorMessage.append(body.getTitle());
        errorMessage.append(" - ");
        errorMessage.append(body.getDetail());
        log.debug("잘못된 요청 입니다 : {}", errorMessage) ;
        return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST);
    }
}
