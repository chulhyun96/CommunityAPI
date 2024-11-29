package com.cheolhyeon.communityapi.module.post.exception;

import com.cheolhyeon.communityapi.common.BaseException;
import com.cheolhyeon.communityapi.module.post.type.PostErrorStatus;
import lombok.Getter;

@Getter
public class PostException extends BaseException {
    private final PostErrorStatus postErrorStatus;
    private final String errorType;
    private final int errorCode;

    public PostException(PostErrorStatus errorStatus) {
        super(errorStatus);
        this.postErrorStatus = errorStatus;
        this.errorType = errorStatus.getErrorType();
        this.errorCode = errorStatus.getErrorCode();
    }
}
