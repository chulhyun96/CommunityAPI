package com.cheolhyeon.communityapi.module.auth.dto;

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

    public static AuthResponse fromEntity(Users save) {
        return AuthResponse.builder()
                .username(save.getUsername())
                .phoneNumber(save.getPhoneNumber())
                .role(save.getRole())
                .build();
    }
    public static AuthResponse create(Users principal) {
        return AuthResponse.builder()
                .username(principal.getUsername())
                .phoneNumber(principal.getPhoneNumber())
                .role(principal.getRole())
                .build();
    }
}
