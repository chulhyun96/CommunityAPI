package com.cheolhyeon.communityapi.module.auth.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AuthRequest {
    @NotNull
    @Size(min = 1, max = 10, message = "회원의 아이디는 1~10글자 사이여야합니다.")
    private String username;

    @NotNull
    @Size(min = 7, max = 15, message = "비밀번호는 최소 7~15글자 사이여야합니다.")
    private String password;

    @NotNull
    @Pattern(regexp = "^01[016789]-\\d{3,4}-\\d{4}$", message = "핸드폰 번호 형식에 어긋납니다.")
    private String phoneNumber;
}
