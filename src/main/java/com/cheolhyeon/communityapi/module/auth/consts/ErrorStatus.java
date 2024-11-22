package com.cheolhyeon.communityapi.module.auth.consts;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorStatus {
    ACCOUNT_NOT_EXISTS(HttpStatus.BAD_REQUEST,"계정이 존재 하지 않습니다."),
    ACCOUNT_HAS_NOT_ROLE(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 오류 발생."),
    JSON_PARSING_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "JSON 데이터 파싱중 오류 발생");


    private final HttpStatus status;
    private final String message;

    ErrorStatus(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
