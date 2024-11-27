package com.cheolhyeon.communityapi.module.post.hateoas;

import com.cheolhyeon.communityapi.module.post.controller.PostController;
import com.cheolhyeon.communityapi.module.post.dto.PostResponsePageable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.EntityModel;

@Configuration
public class AssemblerConfig {
    @Bean
    public PostResourceAssembler postResourceAssembler() {
        return new PostResourceAssembler(PostController.class, (Class<EntityModel<PostResponsePageable>>) (Class<?>) EntityModel.class);
    }
}
