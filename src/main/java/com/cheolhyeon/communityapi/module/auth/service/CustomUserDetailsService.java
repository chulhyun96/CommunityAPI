package com.cheolhyeon.communityapi.module.auth.service;

import com.cheolhyeon.communityapi.module.auth.dto.auth.AuthRequest;
import com.cheolhyeon.communityapi.module.auth.dto.CustomUserDetails;
import com.cheolhyeon.communityapi.module.auth.dto.user.GeneralUserInfo;
import com.cheolhyeon.communityapi.module.auth.dto.user.MyInfoResponse;
import com.cheolhyeon.communityapi.module.auth.entity.Users;
import com.cheolhyeon.communityapi.module.auth.exception.AuthException;
import com.cheolhyeon.communityapi.module.auth.repository.UsersRepository;
import com.cheolhyeon.communityapi.module.auth.type.AuthorityPolicy;
import com.cheolhyeon.communityapi.module.auth.type.AuthErrorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        if (existUser(request)) {
            throw new AuthException(AuthErrorStatus.USER_ALREADY_EXIST);
        }
        return usersRepository.save(Users.create(
                request,
                passwordEncoder.encode(request.getPassword()),
                AuthorityPolicy.ROLE_USER
        ));
    }

    @Transactional
    public Users saveAdmin(AuthRequest request) {
        if (existUser(request)) {
            throw new AuthException(AuthErrorStatus.USER_ALREADY_EXIST);
        }
        return usersRepository.save(Users.create(
                request,
                passwordEncoder.encode(request.getPassword()),
                AuthorityPolicy.ROLE_ADMIN
        ));
    }

    private boolean existUser(AuthRequest request) {
        return usersRepository.findByUsername(request.getUsername()).isPresent();
    }

    public MyInfoResponse getMyInfo(String username) {
        return MyInfoResponse.create(findUser(username, AuthErrorStatus.USER_NOT_FOUND));
    }

    private Users findUser(String username, AuthErrorStatus authErrorStatus) {
        return usersRepository.findByUsername(username)
                .orElseThrow(() -> new AuthException(authErrorStatus));
    }
}
