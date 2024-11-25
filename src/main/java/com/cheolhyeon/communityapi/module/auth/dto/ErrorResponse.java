package com.cheolhyeon.communityapi.module.auth.dto;

import lombok.Getter;
import com.cheolhyeon.communityapi.module.auth.type.ErrorStatus;

@Getter
public class ErrorResponse {
    private final int errorCode;
    private final String errorType;
    private final String message;

    private ErrorResponse(ErrorStatus errorStatus) {
        this.errorCode = errorStatus.getErrorCode();
        this.errorType = errorStatus.getErrorType();
        this.message = errorStatus.getMessage();
    }

    public static ErrorResponse of(ErrorStatus errorStatus) {
        return new ErrorResponse(errorStatus);
    }
}
