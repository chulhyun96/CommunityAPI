package com.cheolhyeon.communityapi.module.post.dto;

import com.cheolhyeon.communityapi.module.post.entity.Post;
import lombok.Getter;


@Getter
public class PostResponse {
    private final String username;
    private final String title;
    private final String content;
    private final Integer commentCount;

    private PostResponse(Post post) {
        this.username = post.getUser().getUsername();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.commentCount = post.getCommentCount();
    }

    public static PostResponse create(Post post) {
        return new PostResponse(post);
    }
}
