package com.cheolhyeon.communityapi.module.auth.dto;

import lombok.Getter;
import com.cheolhyeon.communityapi.module.auth.type.ErrorStatus;

@Getter
public class ErrorResponse {
    private final int errorCode;
    private final String errorType;
    private final String message;
    private final String errorLocation;

    private ErrorResponse(ErrorStatus errorStatus, String errorLocation) {
        this.errorCode = errorStatus.getErrorCode();
        this.errorType = errorStatus.getErrorType();
        this.message = errorStatus.getMessage();
        this.errorLocation = errorLocation;
    }

    public static ErrorResponse of(ErrorStatus errorStatus, String errorLocation) {
        return new ErrorResponse(errorStatus, errorLocation);
    }
}
