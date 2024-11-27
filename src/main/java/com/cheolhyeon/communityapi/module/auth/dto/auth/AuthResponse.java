package com.cheolhyeon.communityapi.module.auth.dto.auth;

import com.cheolhyeon.communityapi.module.auth.entity.Users;
import com.cheolhyeon.communityapi.module.auth.type.AuthorityPolicy;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AuthResponse {
    private String username;
    private String phoneNumber;
    private AuthorityPolicy role;

    public static AuthResponse fromEntity(Users user) {
        return AuthResponse.builder()
                .username(user.getUsername())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .build();
    }
    public static AuthResponse create(Users user) {
        return AuthResponse.builder()
                .username(user.getUsername())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .build();
    }
}
