package com.cheolhyeon.communityapi.module.post.type;

import com.cheolhyeon.communityapi.common.ErrorStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum PostErrorStatus implements ErrorStatus {
    POST_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            HttpStatus.NOT_FOUND.getReasonPhrase(),
            "해당 게시글은 존재 하지 않습니다."
    );

    private final int errorCode;
    private final String errorType;
    private final String message;

    PostErrorStatus(HttpStatus status, String errorType, String message) {
        this.errorCode = status.value();
        this.errorType = errorType;
        this.message = message;
    }
}
