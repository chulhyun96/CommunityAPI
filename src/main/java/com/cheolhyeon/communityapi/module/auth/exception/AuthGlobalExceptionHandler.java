package com.cheolhyeon.communityapi.module.auth.exception;

import com.cheolhyeon.communityapi.module.auth.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class AuthGlobalExceptionHandler {
    @ExceptionHandler(UserAlreadyExistException.class)
    public ErrorResponse userAlreadyExistHandler(UserAlreadyExistException e) {
        return ErrorResponse.of(e.getErrorStatus());
    }
}
