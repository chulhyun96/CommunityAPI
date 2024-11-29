package com.cheolhyeon.communityapi.common;

public interface ErrorStatus {
    int getErrorCode();
    String getErrorType();
    String getMessage();
}
