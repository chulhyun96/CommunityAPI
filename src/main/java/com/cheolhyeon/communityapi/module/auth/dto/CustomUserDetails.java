package com.cheolhyeon.communityapi.module.auth.dto;

import com.cheolhyeon.communityapi.module.auth.entity.Users;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {
    private final Users users;

    public static CustomUserDetails create(Users findUser) {
        return new CustomUserDetails(findUser);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> role = new ArrayList<>();
        role.add(new SimpleGrantedAuthority(users.getRoleAsString()));
        return role;
    }

    @Override
    public String getPassword() {
        return users.getPassword();
    }

    @Override
    public String getUsername() {
        return users.getUsername();
    }

}
