package com.cheolhyeon.communityapi.module.auth.service;

import com.cheolhyeon.communityapi.module.auth.dto.auth.AuthRequest;
import com.cheolhyeon.communityapi.module.auth.dto.CustomUserDetails;
import com.cheolhyeon.communityapi.module.auth.dto.user.UserResponse;
import com.cheolhyeon.communityapi.module.auth.entity.Users;
import com.cheolhyeon.communityapi.module.auth.exception.AuthException;
import com.cheolhyeon.communityapi.module.auth.repository.UsersRepository;
import com.cheolhyeon.communityapi.module.auth.type.AuthorityPolicy;
import com.cheolhyeon.communityapi.module.auth.type.AuthErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users findUser = usersRepository.findByUsername(username)
                .orElseThrow(() -> new AuthException(AuthErrorStatus.ACCOUNT_NOT_EXISTS));

        return CustomUserDetails.create(findUser);
    }

    @Transactional
    public Users saveUser(AuthRequest request) {
        usersRepository.findByUsername(request.getUsername())
                .ifPresent(it -> {
                    throw new AuthException(AuthErrorStatus.USER_ALREADY_EXIST);
                });

        return usersRepository.save(Users.create(
                request,
                passwordEncoder.encode(request.getPassword()),
                AuthorityPolicy.ROLE_USER
        ));
    }

    @Transactional
    public Users saveAdmin(AuthRequest request) {
        usersRepository.findByUsername(request.getUsername())
                .ifPresent(it -> {
                    throw new AuthException(AuthErrorStatus.USER_ALREADY_EXIST);
                });

        return usersRepository.save(Users.create(
                request,
                passwordEncoder.encode(request.getPassword()),
                AuthorityPolicy.ROLE_ADMIN
        ));
    }

    public UserResponse getUser(String username) {
        Users findUser = usersRepository.findByUsername(username)
                .orElseThrow(() -> new AuthException(AuthErrorStatus.USER_NOT_FOUND));
        return UserResponse.create(findUser);
    }
}
