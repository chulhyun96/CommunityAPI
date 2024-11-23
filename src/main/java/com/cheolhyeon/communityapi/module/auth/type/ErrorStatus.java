package com.cheolhyeon.communityapi.module.auth.type;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorStatus {
    ACCOUNT_NOT_EXISTS(
            HttpStatus.BAD_REQUEST,
            HttpStatus.BAD_REQUEST.getReasonPhrase(),
            "해당 계정이 존재하지 않습니다."
    ),
    ACCOUNT_DOESNT_HAVE_NOT_ROLE(
            HttpStatus.INTERNAL_SERVER_ERROR,
            HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
            "계정 조회 - 해당 계좌는 권한이 존재 하지 않습니다."
    ),
    JSON_PARSING_EXCEPTION(
            HttpStatus.INTERNAL_SERVER_ERROR,
            HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
            "JSON Paring 오류 발생"
    );


    private final HttpStatus status;
    private final String error;
    private final String message;

    ErrorStatus(HttpStatus status, String error, String message) {
        this.status = status;
        this.error = error;
        this.message = message;
    }
}

