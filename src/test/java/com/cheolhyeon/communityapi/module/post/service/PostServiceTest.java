package com.cheolhyeon.communityapi.module.post.service;

import com.cheolhyeon.communityapi.module.auth.entity.Users;
import com.cheolhyeon.communityapi.module.auth.exception.AuthException;
import com.cheolhyeon.communityapi.module.auth.repository.UsersRepository;
import com.cheolhyeon.communityapi.module.auth.type.AuthErrorStatus;
import com.cheolhyeon.communityapi.module.auth.type.AuthorityPolicy;
import com.cheolhyeon.communityapi.module.post.dto.PostRequest;
import com.cheolhyeon.communityapi.module.post.dto.PostResponse;
import com.cheolhyeon.communityapi.module.post.entity.Post;
import com.cheolhyeon.communityapi.module.post.exception.PostException;
import com.cheolhyeon.communityapi.module.post.repository.PostRepository;
import com.cheolhyeon.communityapi.module.post.type.PostErrorStatus;
import org.h2.engine.User;
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
class PostServiceTest {
    @Mock
    PostRepository postRepository;

    @Mock
    UsersRepository usersRepository;

    @InjectMocks
    PostService postService;

    @Test
    @DisplayName("게시글 저장 - 성공")
    void save_post_success() {
        //given
        Users user = Users.builder()
                .id(1L)
                .username("Test-Username")
                .password("Test-Password")
                .role(AuthorityPolicy.ROLE_ADMIN)
                .phoneNumber("010-2344-2344")
                .build();

        PostRequest postRequest = PostRequest.builder()
                .title("Test-Title")
                .content("Test-Content")
                .build();

        Post post = Post.create(postRequest);

        given(usersRepository.findByUsername(anyString())).willReturn(Optional.of(user));
        given(postRepository.save(any(Post.class))).willReturn(post);

        //when
        String username = "Test-Username";
        Post savedPost = postService.save(postRequest, username);

        //then
        then(postRepository).should(times(1)).save(any(Post.class));
        then(usersRepository).should(times(1)).findByUsername(anyString());
        assertNotNull(savedPost);
    }
    @Test
    @DisplayName("게시글 저장 - 실패")
    void fail_post_save() {
        //given
        PostRequest postRequest = PostRequest.builder()
                .title("Test-Title")
                .content("Test-Content")
                .build();

        given(usersRepository.findByUsername(anyString())).willReturn(Optional.empty());
        //when
        AuthException exception = assertThrows(AuthException.class,
                () -> postService.save(postRequest, anyString()));
        //then
        then(usersRepository).should(times(1)).findByUsername(anyString());
        assertEquals(AuthErrorStatus.USER_NOT_FOUND.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("게시글 단일 조회 - 성공")
    void success_post_getPost() {
        //given
        Post post = Post.builder()
                .id(1L)
                .user(Users.builder()
                        .username("Test-User")
                        .build())
                .title("Test-Title")
                .content("Test-Content")
                .build();
        given(postRepository.findById(anyLong())).willReturn(Optional.of(post));
        //when
        PostResponse findPost = postService.getPost(1L);
        //then
        then(postRepository).should(times(1)).findById(anyLong());

        assertEquals(post.getTitle(), findPost.getTitle());
        assertEquals(post.getContent(), findPost.getContent());
    }
    @Test
    @DisplayName("게시글 단일 조회 - 실패")
    void fail_post_getPost() {
        //given
        given(postRepository.findById(anyLong())).willReturn(Optional.empty());
        //when
        PostException exception = assertThrows(
                PostException.class, () -> postService.getPost(anyLong())
        );
        //then
        assertEquals(exception.getMessage(), PostErrorStatus.POST_NOT_FOUND.getMessage());
    }
}