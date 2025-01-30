package com.cheolhyeon.communityapi.common.exception;

public interface ErrorStatus {
    int getErrorCode();
    String getErrorType();
    String getMessage();
}
