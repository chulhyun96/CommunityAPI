package com.cheolhyeon.communityapi.module.auth.controller;

import com.cheolhyeon.communityapi.module.auth.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final CustomUserDetailsService customUserDetailsService;

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserDetail(@PathVariable Long id,
                                           Principal principal) {
        return ResponseEntity.ok(customUserDetailsService.getUser(
                id, principal.getName()));
    }
}
