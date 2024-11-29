package com.cheolhyeon.communityapi.common;


import lombok.Getter;

@Getter
public abstract class BaseException extends RuntimeException {
    private final ErrorStatus errorStatus;

    public BaseException(ErrorStatus errorStatus) {
        super(errorStatus.getMessage());
        this.errorStatus = errorStatus;
    }
}
