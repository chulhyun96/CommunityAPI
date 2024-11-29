package com.cheolhyeon.communityapi.module.auth.type;

import com.cheolhyeon.communityapi.common.ErrorStatus;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum AuthErrorStatus implements ErrorStatus {
    ACCOUNT_NOT_EXISTS(
            HttpStatus.BAD_REQUEST,
            HttpStatus.BAD_REQUEST.getReasonPhrase(),
            "해당 계정이 존재하지 않습니다."
    ),
    ACCOUNT_DOESNT_HAVE_NOT_ROLE(
            HttpStatus.INTERNAL_SERVER_ERROR,
            HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
            "계정 조회 - 해당 계정는 권한이 존재 하지 않습니다."
    ),
    JSON_PARSING_EXCEPTION(
            HttpStatus.INTERNAL_SERVER_ERROR,
            HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
            "JSON Paring 오류 발생"
    ),
    USER_ALREADY_EXIST(HttpStatus.CONFLICT,
            HttpStatus.CONFLICT.getReasonPhrase(),
            "사용자 조회 - ID 중복입니다."
    ),
    USER_NOT_FOUND(
            HttpStatus.BAD_REQUEST,
            HttpStatus.BAD_REQUEST.getReasonPhrase(),
            "해당 유저를 찾을 수 없습니다."
    ),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED,
            HttpStatus.UNAUTHORIZED.getReasonPhrase(),
            "타 이용자의 정보를 조회할 수 없습니다."
    ),

    TOKEN_EXPIRED(
            HttpStatus.UNAUTHORIZED,
            HttpStatus.UNAUTHORIZED.getReasonPhrase(),
            "토큰이 만료되었습니다. 로그인해주세요."
    ),
    LOGIN_FIRST(
            HttpStatus.BAD_REQUEST,
            HttpStatus.BAD_REQUEST.getReasonPhrase(),
            "로그인된 사용자만 이용가능합니다."
    );


    private final int errorCode;
    private final String errorType;
    private final String message;

    AuthErrorStatus(HttpStatus status, String errorType, String message) {
        this.errorCode = status.value();
        this.errorType = errorType;
        this.message = message;
    }
}

