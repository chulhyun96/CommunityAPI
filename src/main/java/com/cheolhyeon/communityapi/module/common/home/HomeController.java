package com.cheolhyeon.communityapi.module.common.home;

import com.cheolhyeon.communityapi.module.post.dto.PaginatedResponse;
import com.cheolhyeon.communityapi.module.post.dto.PostResponse;
import com.cheolhyeon.communityapi.module.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequiredArgsConstructor
public class HomeController {
    private final PostService postService;


    @GetMapping("/")
    public ResponseEntity<?> home(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PostResponse> allPosts = postService.getAllPosts(pageable);
        return ResponseEntity.ok(PaginatedResponse.create(allPosts));
    }
}
