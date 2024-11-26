package com.cheolhyeon.communityapi.module.auth.exception;

import com.cheolhyeon.communityapi.module.auth.type.ErrorStatus;
import lombok.Getter;

@Getter
public class UserException extends RuntimeException {
    private final ErrorStatus errorStatus;
    private final String error;
    private final int errorCode;

    public UserException(ErrorStatus errorStats) {
        super(errorStats.getMessage());
        this.errorStatus = errorStats;
        this.errorCode = errorStats.getErrorCode();
        this.error = errorStats.getErrorType();
    }
}
