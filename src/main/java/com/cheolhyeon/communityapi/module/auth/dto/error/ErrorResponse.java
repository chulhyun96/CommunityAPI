package com.cheolhyeon.communityapi.module.auth.dto.error;

import lombok.Getter;
import com.cheolhyeon.communityapi.module.auth.type.AuthErrorStatus;

@Getter
public class ErrorResponse {
    private final int errorCode;
    private final String errorType;
    private final String message;

    private ErrorResponse(AuthErrorStatus authErrorStatus) {
        this.errorCode = authErrorStatus.getErrorCode();
        this.errorType = authErrorStatus.getErrorType();
        this.message = authErrorStatus.getMessage();
    }

    public static ErrorResponse create(AuthErrorStatus authErrorStatus) {
        return new ErrorResponse(authErrorStatus);
    }
}
