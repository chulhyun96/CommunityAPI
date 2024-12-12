package com.cheolhyeon.communityapi.module.auth.service;

import com.cheolhyeon.communityapi.module.auth.dto.auth.AuthRequest;
import com.cheolhyeon.communityapi.module.auth.dto.user.GeneralUserInfoResponse;
import com.cheolhyeon.communityapi.module.auth.dto.user.MyInfoResponse;
import com.cheolhyeon.communityapi.module.auth.entity.Users;
import com.cheolhyeon.communityapi.module.auth.exception.AuthException;
import com.cheolhyeon.communityapi.module.auth.repository.UsersRepository;
import com.cheolhyeon.communityapi.module.auth.type.AuthorityPolicy;
import com.cheolhyeon.communityapi.module.auth.type.AuthErrorStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {
    @Mock
    UsersRepository usersRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    CustomUserDetailsService userService;

    @BeforeEach
    void setUp() {
        this.userService = new CustomUserDetailsService(usersRepository,passwordEncoder);
    }

    @Test
    @DisplayName("회원가입 - (유저)성공")
    void save_User_success() {
        //given
        AuthRequest request = AuthRequest.builder()
                .username("Test1")
                .password("password")
                .phoneNumber("010-123-4567")
                .build();

        String encodedPassword = "encodedPassword";

        Users entity = Users.builder()
                .username(request.getUsername())
                .password(encodedPassword)
                .phoneNumber(request.getPhoneNumber())
                .role(AuthorityPolicy.ROLE_USER)
                .build();

        given(usersRepository.save(any(Users.class))).willReturn(entity);

        //when
        Users savedUser = userService.saveUser(request);

        //then
        then(usersRepository).should(times(1)).save(any(Users.class));

        assertAll(
                () -> assertNotNull(savedUser),
                () -> assertEquals(request.getUsername(), entity.getUsername()),
                () -> assertEquals(request.getPhoneNumber(), entity.getPhoneNumber())
        );
    }
    @Test
    @DisplayName("회원가입 - (유저) 실패 - 회원 ID 중복")
    void fail_user_success() {
        // Given
        AuthRequest request = AuthRequest.builder()
                .username("duplicateUser")
                .password("password")
                .phoneNumber("010-123-4567")
                .build();

        Users existingUser = Users.builder()
                .username(request.getUsername())
                .password("encodedPassword")
                .phoneNumber(request.getPhoneNumber())
                .role(AuthorityPolicy.ROLE_USER)
                .build();

        given(usersRepository.findByUsername(request.getUsername()))
                .willReturn(Optional.of(existingUser));

        // When
        AuthException exception = assertThrows(AuthException.class,
                () -> userService.saveUser(request));

        // Then
        then(usersRepository).should(times(0)).save(any(Users.class));
        then(usersRepository).should(times(1)).findByUsername(request.getUsername());
        then(usersRepository).shouldHaveNoMoreInteractions();


        assertEquals(AuthErrorStatus.USER_ALREADY_EXIST.getMessage(), exception.getMessage());

    }
    @Test
    @DisplayName("내 정보 조회 - 성공")
    void success_get_MyInfo() {
        //given
        MyInfoResponse myInfoResponse = MyInfoResponse.create(
                "TestA", "010-123-4567", AuthorityPolicy.ROLE_USER, 1L, 1L
        );
        given(usersRepository.findMyInfoByUsernameWithPostCommentDto(anyString())).willReturn(
                Optional.of(myInfoResponse));

        //when
        MyInfoResponse response = userService.getMyInfo("TestA");

        //then
        then(usersRepository).should(times(1)).findMyInfoByUsernameWithPostCommentDto(anyString());
        assertEquals(myInfoResponse, response);
    }
    @Test
    @DisplayName("타 유저 정보 조회 - 성공")
    void success_get_UserInfo() {
        //given
        Users user = Users.builder()
                .id(1L)
                .username("Test1")
                .password("password")
                .phoneNumber("010-123-4567")
                .role(AuthorityPolicy.ROLE_USER)
                .posts(new ArrayList<>())
                .comments(new ArrayList<>())
                .build();
        given(usersRepository.findByUsername(anyString()))
                .willReturn(Optional.of(user));

        //when
        GeneralUserInfoResponse userInfo = userService.getUserInfo(user.getUsername());

        //then
        then(usersRepository).should(times(1)).findByUsername(anyString());
        assertEquals(userInfo.getUsername(), user.getUsername());

    }
    @Test
    @DisplayName("타 유저 정보 조회 - 실패")
    void fail_get_UserInfo() {
        //given
        given(usersRepository.findByUsername(anyString()))
                .willReturn(Optional.empty());

        //when
        Assertions.assertThrows(AuthException.class,
                () -> userService.getUserInfo(anyString()));

        //then
        then(usersRepository).should(times(1)).findByUsername(anyString());
        assertEquals(AuthErrorStatus.USER_NOT_FOUND.getMessage(),"해당 유저를 찾을 수 없습니다.");

    }
}