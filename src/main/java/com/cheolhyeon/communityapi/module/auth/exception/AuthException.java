package com.cheolhyeon.communityapi.module.auth.exception;

import com.cheolhyeon.communityapi.common.exception.BaseException;
import com.cheolhyeon.communityapi.module.auth.type.AuthErrorStatus;
import lombok.Getter;

@Getter
public class AuthException extends BaseException {
    private final AuthErrorStatus authErrorStatus;
    private final String errorType;
    private final int errorCode;

    public AuthException(AuthErrorStatus errorStats) {
        super(errorStats);
        this.authErrorStatus = errorStats;
        this.errorCode = errorStats.getErrorCode();
        this.errorType = errorStats.getErrorType();
    }
}
