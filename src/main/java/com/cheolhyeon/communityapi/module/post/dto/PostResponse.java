package com.cheolhyeon.communityapi.module.post.dto;

import com.cheolhyeon.communityapi.module.comment.entity.Comment;
import com.cheolhyeon.communityapi.module.post.entity.Post;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
public class PostResponse {
    private final String username;
    private final String title;
    private final String content;
    private final Integer commentCount;
    private final List<Comment> comments = new ArrayList<>();
    private final LocalDate createdAt;

    private PostResponse(Post post) {
        this.username = post.getUser().getUsername();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.commentCount = 0;
        this.createdAt = post.getCreatedAt();
    }

    public static PostResponse create(Post post) {
        return new PostResponse(post);
    }
}
