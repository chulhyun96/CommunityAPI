package com.cheolhyeon.communityapi.module.auth.service;

import com.cheolhyeon.communityapi.module.auth.dto.AuthRequest;
import com.cheolhyeon.communityapi.module.auth.dto.CustomUserDetails;
import com.cheolhyeon.communityapi.module.auth.entity.Users;
import com.cheolhyeon.communityapi.module.auth.exception.UserAlreadyExistException;
import com.cheolhyeon.communityapi.module.auth.repository.UsersRepository;
import com.cheolhyeon.communityapi.module.auth.type.ErrorStatus;
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
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return CustomUserDetails.from(findUser);
    }

    @Transactional
    public Users saveUser(AuthRequest request) {
        usersRepository.findByUsername(request.getUsername())
                .ifPresent(it -> {
                    throw new UserAlreadyExistException(ErrorStatus.USER_ALREADY_EXIST);
                });

        return usersRepository.save(Users.createUser(request,
                passwordEncoder.encode(request.getPassword())));
    }

    @Transactional
    public Users saveAdmin(AuthRequest request) {
        usersRepository.findByUsername(request.getUsername())
                .ifPresent(it -> {
                    throw new UserAlreadyExistException(ErrorStatus.USER_ALREADY_EXIST);
                });

        return usersRepository.save(Users.createAdmin(request,
                passwordEncoder.encode(request.getPassword())));
    }
}
