package com.cheolhyeon.communityapi.module.comment.dto;

import lombok.Getter;

@Getter
public class CommentOfPostContent {
    private final int index;
    private final String username;
    private final String content;

    private CommentOfPostContent(int index ,String username, String content) {
        this.index = index;
        this.username = username;
        this.content = content;
    }
    public static CommentOfPostContent create(int index,String username, String content) {
        return new CommentOfPostContent(index, username, content);
    }
}
