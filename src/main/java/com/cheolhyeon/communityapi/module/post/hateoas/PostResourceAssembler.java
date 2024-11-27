package com.cheolhyeon.communityapi.module.post.hateoas;

import com.cheolhyeon.communityapi.module.post.controller.PostController;
import com.cheolhyeon.communityapi.module.post.dto.PostResponsePageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


public class PostResourceAssembler extends RepresentationModelAssemblerSupport<PostResponsePageable, EntityModel<PostResponsePageable>> {

    public PostResourceAssembler(Class<?> controllerClass, Class<EntityModel<PostResponsePageable>> resourceType) {
        super(controllerClass, resourceType);
    }

    @Override
    public EntityModel<PostResponsePageable> toModel(PostResponsePageable postResponse) {
        EntityModel<PostResponsePageable> postModel = EntityModel.of(postResponse);

        Link detailLink = linkTo(methodOn(PostController.class)
                .getPost(postResponse.getPostId()))
                .withRel("detail");

        postModel.add(detailLink);
        return postModel;
    }
}
