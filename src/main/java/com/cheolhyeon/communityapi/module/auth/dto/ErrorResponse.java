package com.cheolhyeon.communityapi.module.auth.dto;

import lombok.Getter;
import com.cheolhyeon.communityapi.module.auth.type.ErrorStatus;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResponse {
    private final HttpStatus status;
    private final String error;
    private final String message;
    private final String errorLocation;

    private ErrorResponse(ErrorStatus errorStatus, String errorLocation) {
        this.status = errorStatus.getStatus();
        this.error = errorStatus.getError();
        this.message = errorStatus.getMessage();
        this.errorLocation = errorLocation;
    }

    public static ErrorResponse of(ErrorStatus errorStatus, String errorLocation) {
        return new ErrorResponse(errorStatus, errorLocation);
    }
}
