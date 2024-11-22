package com.cheolhyeon.communityapi.module.auth.dto;

import com.cheolhyeon.communityapi.module.auth.consts.ErrorStatus;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResponse {
    private final HttpStatus status;
    private final String message;

    private ErrorResponse(ErrorStatus errorStatus) {
        this.status = errorStatus.getStatus();
        this.message = errorStatus.getMessage();
    }

    public static ErrorResponse create(ErrorStatus errorStatus) {
        return new ErrorResponse(errorStatus);
    }
}
