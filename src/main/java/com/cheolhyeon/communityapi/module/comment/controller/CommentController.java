package com.cheolhyeon.communityapi.module.comment.controller;

import com.cheolhyeon.communityapi.common.ErrorResponseBindingResult;
import com.cheolhyeon.communityapi.module.comment.dto.CommentRequest;
import com.cheolhyeon.communityapi.module.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/post/{postId}/comment")
    public ResponseEntity<?> save(@PathVariable Long postId,
                                  Principal principal,
                                  @RequestBody @Validated CommentRequest commentRequest,
                                  BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(
                    ErrorResponseBindingResult.fromBindingResult(
                            bindingResult.getFieldErrors()
                    ));
        }
        return ResponseEntity.ok(commentService.save(postId, principal.getName(), commentRequest));
    }
}
