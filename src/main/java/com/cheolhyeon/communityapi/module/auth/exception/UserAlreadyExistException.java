package com.cheolhyeon.communityapi.module.auth.exception;

import com.cheolhyeon.communityapi.module.auth.type.ErrorStatus;
import lombok.Getter;

@Getter
public class UserAlreadyExistException extends RuntimeException {
    private final ErrorStatus errorStatus;
    private final String error;
    private final int errorCode;

    public UserAlreadyExistException(ErrorStatus errorStats) {
        super(errorStats.getMessage());
        this.errorStatus = errorStats;
        this.errorCode = errorStats.getErrorCode();
        this.error = errorStats.getErrorType();
    }
}
