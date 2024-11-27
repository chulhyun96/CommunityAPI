package com.cheolhyeon.communityapi.module.post.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PostRequest {
    private String title;
    private String content;
}
