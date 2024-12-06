package com.cheolhyeon.communityapi.module.auth.controller;

import com.cheolhyeon.communityapi.module.auth.dto.CustomUserDetails;
import com.cheolhyeon.communityapi.module.auth.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final CustomUserDetailsService userService;

    /**
     * 내 정보 보기
     */
    @GetMapping("/user")
    public ResponseEntity<?> getMyInfoDetail(Authentication authentication) {
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(userService.getMyInfo(user.getUsername()));
    }

    /**
     * 타인 정보 보기
     */
    @GetMapping("/user/{username}")
    public ResponseEntity<?> getUserInfo(@PathVariable("username") String username) {
        return ResponseEntity.ok(userService.getUserInfo(username));
    }
}
