package com.cheolhyeon.communityapi.module.auth.exception;

import com.cheolhyeon.communityapi.module.auth.controller.AuthController;
import com.cheolhyeon.communityapi.module.auth.dto.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = AuthController.class)
public class AuthGlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistException.class)
    public ErrorResponse userAlreadyExistHandler(UserAlreadyExistException e) {
        return ErrorResponse.of(e.getErrorStatus());
    }
}
