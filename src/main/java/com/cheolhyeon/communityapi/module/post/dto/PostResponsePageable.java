package com.cheolhyeon.communityapi.module.post.dto;

import com.cheolhyeon.communityapi.module.post.entity.Post;
import lombok.Getter;


@Getter
public class PostResponsePageable {
    private final Long postId;
    private final String username;
    private final String title;

    private PostResponsePageable(Post post) {
        this.postId = post.getId();
        this.username = post.getUser().getUsername();
        this.title = post.getTitle();
    }

    public static PostResponsePageable create(Post post) {
        return new PostResponsePageable(post);
    }
}
