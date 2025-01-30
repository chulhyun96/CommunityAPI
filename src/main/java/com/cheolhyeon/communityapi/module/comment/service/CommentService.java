package com.cheolhyeon.communityapi.module.comment.service;

import com.cheolhyeon.communityapi.module.auth.entity.Users;
import com.cheolhyeon.communityapi.module.auth.exception.AuthException;
import com.cheolhyeon.communityapi.module.auth.repository.UsersRepository;
import com.cheolhyeon.communityapi.module.auth.type.AuthErrorStatus;
import com.cheolhyeon.communityapi.module.comment.dto.CommentRequest;
import com.cheolhyeon.communityapi.module.comment.dto.CommentResponse;
import com.cheolhyeon.communityapi.module.comment.entity.Comment;
import com.cheolhyeon.communityapi.module.comment.repository.CommentRepository;
import com.cheolhyeon.communityapi.module.post.entity.Post;
import com.cheolhyeon.communityapi.module.post.exception.PostException;
import com.cheolhyeon.communityapi.module.post.repository.PostRepository;
import com.cheolhyeon.communityapi.module.post.type.PostErrorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UsersRepository usersRepository;

    @Transactional
    public CommentResponse save(Long postId, String username, CommentRequest commentRequest) {
        log.info("Saving comment for Post = PostId = {}, RequestUser = {}, Comment = {}", postId, username, commentRequest);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(PostErrorStatus.POST_NOT_FOUND));
        Users user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new AuthException(AuthErrorStatus.USER_NOT_FOUND));
        Comment comment = commentRepository.save(Comment.create(commentRequest));

        post.addComment(comment);
        user.addComment(comment);
        return CommentResponse.create(comment, post, user);
    }
}
