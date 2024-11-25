package com.cheolhyeon.communityapi.module.auth.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AuthRequest {
    @NotBlank(message = "아이디를 입력해주세요.")
    @Size(min = 1, max = 10, message = "회원의 아이디는 1~10글자 사이여야합니다.")
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 7, max = 15, message = "비밀번호는 최소 7~15글자 사이여야합니다.")
    private String password;

    @NotBlank(message = "핸드폰 번호를 입력해주세요.")
    @Pattern(regexp = "^01[016789]-\\d{3,4}-\\d{4}$", message = "입력 형식에 어긋납니다.")
    private String phoneNumber;
}
