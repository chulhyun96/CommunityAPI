package com.cheolhyeon.communityapi.module.post.dto;

import com.cheolhyeon.communityapi.module.post.entity.Post;
import lombok.Getter;

import java.time.LocalDate;


@Getter
public class PostResponsePageable {
    private final Long postId;
    private final String username;
    private final String title;
    private final Integer commentCount;
    private final LocalDate createdDate;

    private PostResponsePageable(Post post) {
        this.postId = post.getId();
        this.username = post.getUser().getUsername();
        this.title = post.getTitle();
        this.createdDate = post.getCreatedAt();
        this.commentCount = post.getCommentCount();
    }

    public static PostResponsePageable create(Post post) {
        return new PostResponsePageable(post);
    }
}
