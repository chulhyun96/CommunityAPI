package com.cheolhyeon.communityapi.module.post.dto;

import com.cheolhyeon.communityapi.module.comment.dto.CommentOfPostContent;
import com.cheolhyeon.communityapi.module.comment.entity.Comment;
import com.cheolhyeon.communityapi.module.post.entity.Post;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
public class PostGetResponse {
    private final Long postId;
    private final String title;
    private final String postContent;
    private final List<CommentOfPostContent> list;
    private final String username;
    private final int countOfComments;

    private PostGetResponse(Post post, String username, List<Comment> comments) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.postContent = post.getContent();
        this.username = username;
        this.list = IntStream.range(0, comments.size())
                .mapToObj(i -> CommentOfPostContent.create(
                        i+1,
                        comments.get(i).getUser().getUsername(),
                        comments.get(i).getContent()
                ))
                .collect(Collectors.toList());
        this.countOfComments = comments.size();
    }

    public static PostGetResponse create(Post post, String username, List<Comment> comments) {
        return new PostGetResponse(post, username, comments);
    }
}
