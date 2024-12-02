package com.cheolhyeon.communityapi.module.comment.service;

import com.cheolhyeon.communityapi.module.auth.entity.Users;
import com.cheolhyeon.communityapi.module.comment.dto.CommentRequest;
import com.cheolhyeon.communityapi.module.comment.dto.CommentResponse;
import com.cheolhyeon.communityapi.module.comment.entity.Comment;
import com.cheolhyeon.communityapi.module.comment.repository.CommentRepository;
import com.cheolhyeon.communityapi.module.post.entity.Post;
import com.cheolhyeon.communityapi.module.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostService postService;

    @Transactional
    public CommentResponse save(Long postId, CommentRequest commentRequest) {
        Post post = postService.getPost(postId); // user의 정보도 반환됨
        Users user = post.getUser();
        Comment comment = commentRepository.save(Comment.create(commentRequest));
        post.addComment(comment);
        user.addComment(comment);
        return CommentResponse.create(comment, post, user);
    }
}
