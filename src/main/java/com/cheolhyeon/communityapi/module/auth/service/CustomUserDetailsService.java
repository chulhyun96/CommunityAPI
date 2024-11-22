package com.cheolhyeon.communityapi.module.auth.service;

import com.cheolhyeon.communityapi.module.auth.dto.CustomUserDetails;
import com.cheolhyeon.communityapi.module.auth.entity.Users;
import com.cheolhyeon.communityapi.module.auth.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UsersRepository usersRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users findUser = usersRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return CustomUserDetails.from(findUser);
    }
}
