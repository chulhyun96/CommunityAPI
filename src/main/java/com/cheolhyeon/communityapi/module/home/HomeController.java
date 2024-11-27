package com.cheolhyeon.communityapi.module.home;

import com.cheolhyeon.communityapi.module.post.controller.PostController;
import com.cheolhyeon.communityapi.module.post.dto.PostResponse;
import com.cheolhyeon.communityapi.module.post.dto.PostResponsePageable;
import com.cheolhyeon.communityapi.module.post.entity.Post;
import com.cheolhyeon.communityapi.module.post.hateoas.PostResourceAssembler;
import com.cheolhyeon.communityapi.module.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequiredArgsConstructor
public class HomeController {
    private final PostService postService;
    private final PostResourceAssembler postResourceAssembler;


    @GetMapping("/home")
    public ResponseEntity<PagedModel<EntityModel<PostResponsePageable>>> home(
            @PageableDefault(sort = "id") Pageable pageable,
            PagedResourcesAssembler<PostResponsePageable> assembler) {

        Page<PostResponsePageable> postAll = postService.getAllPosts(pageable);
        PagedModel<EntityModel<PostResponsePageable>> model = assembler.toModel(postAll,postResourceAssembler);
        return ResponseEntity.ok(model);
    }
}
