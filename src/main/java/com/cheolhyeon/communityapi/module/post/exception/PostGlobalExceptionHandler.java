package com.cheolhyeon.communityapi.module.post.exception;

import com.cheolhyeon.communityapi.common.ErrorResponse;
import com.cheolhyeon.communityapi.module.post.controller.PostController;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = PostController.class)
public class PostGlobalExceptionHandler {

    @ExceptionHandler(PostException.class)
    public ErrorResponse handlePostException(PostException e) {
        return ErrorResponse.create(e.getPostErrorStatus());
    }
}
