package com.cheolhyeon.communityapi.module.post.controller;

import com.cheolhyeon.communityapi.module.post.dto.PostRequest;
import com.cheolhyeon.communityapi.module.post.dto.PostResponse;
import com.cheolhyeon.communityapi.module.post.dto.PostResponsePageable;
import com.cheolhyeon.communityapi.module.post.entity.Post;
import com.cheolhyeon.communityapi.module.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/post")
    public ResponseEntity<?> post(@RequestBody PostRequest postRequest,
                                  Principal principal) {
        return ResponseEntity.ok().body(PostResponsePageable.create(
                postService.save(postRequest, principal.getName())
        ));
    }
    @GetMapping("/post/{id}")
    public ResponseEntity<?> getPost(@PathVariable Long id) {
        Post post = postService.getPost(id);
        return ResponseEntity.ok(PostResponse.create(post));
    }
}
