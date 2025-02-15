package com.cheolhyeon.communityapi.common.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {
    private final int errorCode;
    private final String errorType;
    private final String message;

    private ErrorResponse(ErrorStatus authErrorStatus) {
        this.errorCode = authErrorStatus.getErrorCode();
        this.errorType = authErrorStatus.getErrorType();
        this.message = authErrorStatus.getMessage();
    }

    public static ErrorResponse create(ErrorStatus authErrorStatus) {
        return new ErrorResponse(authErrorStatus);
    }
}
