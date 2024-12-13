package com.cheolhyeon.communityapi.module.comment.service;

import com.cheolhyeon.communityapi.module.auth.entity.Users;
import com.cheolhyeon.communityapi.module.auth.repository.UsersRepository;
import com.cheolhyeon.communityapi.module.comment.dto.CommentRequest;
import com.cheolhyeon.communityapi.module.comment.dto.CommentResponse;
import com.cheolhyeon.communityapi.module.comment.entity.Comment;
import com.cheolhyeon.communityapi.module.comment.repository.CommentRepository;
import com.cheolhyeon.communityapi.module.post.entity.Post;
import com.cheolhyeon.communityapi.module.post.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;


@ExtendWith(MockitoExtension.class)
class CommentServiceTest {
    @Mock
    CommentRepository commentRepository;
    @Mock
    PostRepository postRepository;
    @Mock
    UsersRepository usersRepository;

    @InjectMocks
    CommentService commentService;


    @Test
    @DisplayName("댓글 작성 성공")
    void success_comment_save() {
        //given
        Long postId = 1L;
        String username = "Test_User1";
        CommentRequest commentRequest = new CommentRequest("Test_Comment");
        Comment comment = Comment.create(commentRequest);
        given(postRepository.findById(anyLong())).willReturn(Optional.of(new Post()));
        given(usersRepository.findByUsername(anyString())).willReturn(Optional.of(new Users()));
        given(commentRepository.save(any(Comment.class))).willReturn(Comment.create(commentRequest));

        //when
        CommentResponse savedCommentResponse = commentService.save(
                postId, username, commentRequest
        );

        //then
        then(commentRepository).should(times(1)).save(any(Comment.class));
        then(postRepository).should(times(1)).findById(postId);
        then(usersRepository).should(times(1)).findByUsername(username);
        assertEquals(savedCommentResponse.getCommentContent(), comment.getContent());
    }
}