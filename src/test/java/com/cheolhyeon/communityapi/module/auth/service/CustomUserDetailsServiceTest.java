package com.cheolhyeon.communityapi.module.auth.service;

import com.cheolhyeon.communityapi.module.auth.dto.AuthRequest;
import com.cheolhyeon.communityapi.module.auth.entity.Users;
import com.cheolhyeon.communityapi.module.auth.exception.UserAlreadyExist;
import com.cheolhyeon.communityapi.module.auth.repository.UsersRepository;
import com.cheolhyeon.communityapi.module.auth.type.AuthorityPolicy;
import com.cheolhyeon.communityapi.module.auth.type.ErrorStatus;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
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
    @DisplayName("회원가입 - 성공")
    void save_success() {
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

        given(usersRepository.save(any(Users.class)))
                .willReturn(entity);
        //when
        Users savedUser = customUserDetailsService.save(request);
        ArgumentCaptor<Users> captor = ArgumentCaptor.forClass(Users.class);
        //then
        verify(usersRepository, times(1)).save(captor.capture());
        assertAll(
                () -> assertNotNull(savedUser),
                () -> assertEquals(request.getUsername(), entity.getUsername()),
                () -> assertEquals(request.getPhoneNumber(), entity.getPhoneNumber())
        );
    }
    @Test
    @DisplayName("회원가입 - 실패(회원 ID 중복)")
    void fail_success() {
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
        UserAlreadyExist exception = assertThrows(UserAlreadyExist.class,
                () -> customUserDetailsService.save(request));

        // Then
        verify(usersRepository, times(1))
                .findByUsername(request.getUsername());
        verifyNoMoreInteractions(usersRepository);
        assertEquals(ErrorStatus.USER_ALREADY_EXIST.getMessage(), exception.getMessage());

    }
}