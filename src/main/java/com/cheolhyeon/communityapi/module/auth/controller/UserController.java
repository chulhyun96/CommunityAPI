package com.cheolhyeon.communityapi.module.auth.controller;

import com.cheolhyeon.communityapi.module.auth.dto.user.UserResponse;
import com.cheolhyeon.communityapi.module.auth.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final CustomUserDetailsService customUserDetailsService;

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserDetail(@PathVariable Long id) {
        return ResponseEntity.ok().body(UserResponse.create(
                customUserDetailsService.getUser(id)
        ));
    }
}
