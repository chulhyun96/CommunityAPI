package com.cheolhyeon.communityapi.module.auth.exception;

import com.cheolhyeon.communityapi.module.auth.controller.AuthController;
import com.cheolhyeon.communityapi.module.auth.controller.UserController;
import com.cheolhyeon.communityapi.module.auth.dto.error.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = {AuthController.class, UserController.class})
public class AuthGlobalExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ErrorResponse userExceptionHandler(UserException e) {
        return ErrorResponse.of(e.getErrorStatus());
    }
}
