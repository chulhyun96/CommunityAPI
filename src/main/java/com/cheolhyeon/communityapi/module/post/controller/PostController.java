package com.cheolhyeon.communityapi.module.post.controller;

import com.cheolhyeon.communityapi.common.ErrorResponseBindingResult;
import com.cheolhyeon.communityapi.module.post.dto.PostGetResponse;
import com.cheolhyeon.communityapi.module.post.dto.PostRequest;
import com.cheolhyeon.communityapi.module.post.dto.PostResponse;
import com.cheolhyeon.communityapi.module.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    /**
     * 게시글 등록
     */
    @PostMapping("/post")
    public ResponseEntity<?> save(@RequestBody @Validated PostRequest postRequest,
                                  BindingResult bindingResult,
                                  Principal principal) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(
                    ErrorResponseBindingResult.fromBindingResult(
                    bindingResult.getFieldErrors()
            ));
        }
        return ResponseEntity.ok().body(PostResponse.create(
                postService.save(postRequest, principal.getName())
        ));
    }

    /**
     * 게시글 조회
     */
    @GetMapping("/post/{id}")
    public ResponseEntity<?> getPost(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPost(id));
    }
}
