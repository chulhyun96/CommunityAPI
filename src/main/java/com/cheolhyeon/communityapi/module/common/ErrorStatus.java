package com.cheolhyeon.communityapi.module.common;

public interface ErrorStatus {
    int getErrorCode();
    String getErrorType();
    String getMessage();
}
