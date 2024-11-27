package com.cheolhyeon.communityapi.module.auth.service;

import com.cheolhyeon.communityapi.module.auth.dto.auth.AuthRequest;
import com.cheolhyeon.communityapi.module.auth.dto.user.UserResponse;
import com.cheolhyeon.communityapi.module.auth.entity.Users;
import com.cheolhyeon.communityapi.module.auth.exception.UserException;
import com.cheolhyeon.communityapi.module.auth.repository.UsersRepository;
import com.cheolhyeon.communityapi.module.auth.type.AuthorityPolicy;
import com.cheolhyeon.communityapi.module.auth.type.ErrorStatus;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@Transactional
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {
    @Mock
    UsersRepository usersRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    void setUp() {
        this.customUserDetailsService = new CustomUserDetailsService(usersRepository,passwordEncoder);
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
        Users savedUser = customUserDetailsService.saveUser(request);

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
        UserException exception = assertThrows(UserException.class,
                () -> customUserDetailsService.saveUser(request));

        // Then
        then(usersRepository).should(times(0)).save(any(Users.class));
        then(usersRepository).should(times(1)).findByUsername(request.getUsername());
        then(usersRepository).shouldHaveNoMoreInteractions();


        assertEquals(ErrorStatus.USER_ALREADY_EXIST.getMessage(), exception.getMessage());

    }
    @Test
    @DisplayName("유저 조회 - 성공")
    void success_get_user() {
        //given
        Users user = Users.builder()
                .id(1L)
                .username("Test1")
                .password("password")
                .phoneNumber("010-123-4567")
                .role(AuthorityPolicy.ROLE_USER)
                .build();
        given(usersRepository.findById(anyLong())).willReturn(Optional.of(user));
        given(usersRepository.findByUsername(anyString())).willReturn(Optional.of(user));
        //when
        UserResponse findUser = customUserDetailsService.getUser(1L,anyString());
        //then
        then(usersRepository).should(times(1)).findById(anyLong());
        assertEquals(user.getUsername(), findUser.getUsername());
    }

    @Test
    @DisplayName("유저 조회 - 실패")
    void fail_get_user() {
        //given
        given(usersRepository.findById(anyLong())).willReturn(Optional.empty());
        given(usersRepository.findByUsername(anyString())).willReturn(Optional.empty());
        //when
        assertThrows(UserException.class, () -> customUserDetailsService.getUser(1L,"Test1"));
        //then
        then(usersRepository).should(times(1)).findById(anyLong());
    }
}