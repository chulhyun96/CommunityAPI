package com.cheolhyeon.communityapi.module.post.dto;

import com.cheolhyeon.communityapi.module.post.entity.Post;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class PostResponse implements Comparable<PostResponse> {
    private final String username;
    private final String title;
    private final Integer commentCount;
    private final LocalDate createdAt;

    private PostResponse(Post post) {
        this.username = post.getUser().getUsername();
        this.title = post.getTitle();
        this.commentCount = post.getCommentCount();
        this.createdAt = post.getCreatedAt();
    }

    public static PostResponse create(Post post) {
        return new PostResponse(post);
    }

    @Override
    public int compareTo(PostResponse o) {
        return Integer.compare(o.commentCount, this.commentCount);
    }
}
