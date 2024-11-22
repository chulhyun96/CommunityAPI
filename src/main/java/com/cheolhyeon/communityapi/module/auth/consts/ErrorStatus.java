package com.cheolhyeon.communityapi.module.auth.consts;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorStatus {
    ACCOUNT_NOT_EXISTS(HttpStatus.BAD_REQUEST,"계정이 존재 하지 않습니다.");

    private final HttpStatus status;
    private final String message;

    ErrorStatus(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

}
