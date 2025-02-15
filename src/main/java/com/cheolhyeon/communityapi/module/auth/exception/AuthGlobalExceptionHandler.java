package com.cheolhyeon.communityapi.module.auth.exception;

import com.cheolhyeon.communityapi.common.exception.ErrorResponse;
import com.cheolhyeon.communityapi.module.auth.controller.AuthController;
import com.cheolhyeon.communityapi.module.auth.controller.UserController;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.util.StringUtils;
import org.springframework.util.StringUtils;
import org.springframework.util.StringUtils;
import org.springframework.util.StringUtils;
import org.springframework.util.StringUtils;
import org.springframework.util.StringUtils;
import org.springframework.util.StringUtils;
import org.springframework.util.StringUtils;
import org.springframework.util.StringUtils;

@RestControllerAdvice(assignableTypes = {AuthController.class, UserController.class})
public class AuthGlobalExceptionHandler {

    @ExceptionHandler(AuthException.class)
    public ErrorResponse userExceptionHandler(AuthException e) {
        return ErrorResponse.create(e.getAuthErrorStatus());
    }
}
