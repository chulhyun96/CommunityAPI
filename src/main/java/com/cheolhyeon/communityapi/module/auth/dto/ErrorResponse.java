package com.cheolhyeon.communityapi.module.auth.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResponse {
    private final HttpStatus status;
    private final String message;

    private ErrorResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
    public static ErrorResponse create(HttpStatus status, String message) {
        return new ErrorResponse(status, message);
    }
}
