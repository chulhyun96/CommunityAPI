package com.cheolhyeon.communityapi.module.comment.dto;

import com.cheolhyeon.communityapi.module.auth.entity.Users;
import com.cheolhyeon.communityapi.module.comment.entity.Comment;
import com.cheolhyeon.communityapi.module.post.entity.Post;
import lombok.Getter;

@Getter
public class CommentResponse {
    private final Long commentId;
    private final String commentContent;
    private final Long postId;
    private final String postTitle;
    private final String username;

    private CommentResponse(Comment comment, Post post, Users user) {
        this.postId = post.getId();
        this.postTitle = post.getTitle();
        this.commentId = comment.getId();
        this.commentContent = comment.getContent();
        this.username = user.getUsername();
    }
    public static CommentResponse create(Comment comment, Post post, Users user) {
        return new CommentResponse(comment, post, user);
    }
}
