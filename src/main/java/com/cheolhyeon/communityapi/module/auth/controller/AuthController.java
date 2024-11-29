package com.cheolhyeon.communityapi.module.auth.controller;

import com.cheolhyeon.communityapi.module.auth.dto.auth.AuthRequest;
import com.cheolhyeon.communityapi.module.auth.dto.auth.AuthResponse;
import com.cheolhyeon.communityapi.module.common.ErrorResponseBindingResult;
import com.cheolhyeon.communityapi.module.auth.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final CustomUserDetailsService customUserDetailsService;

    @PostMapping("/signup/user")
    public ResponseEntity<?> saveUser(@RequestBody @Validated AuthRequest request,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(
                    ErrorResponseBindingResult.fromBindingResult(
                            bindingResult.getFieldErrors()
                    ));
        }
        return ResponseEntity.ok().body(AuthResponse.fromEntity(
                customUserDetailsService.saveUser(request)
        ));
    }

    @PostMapping("/signup/admin")
    public ResponseEntity<?> saveAdmin(@RequestBody @Validated AuthRequest request,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(
                    ErrorResponseBindingResult.fromBindingResult(
                            bindingResult.getFieldErrors()
                    ));
        }
        return ResponseEntity.ok().body(AuthResponse.fromEntity(
                customUserDetailsService.saveAdmin(request)
        ));
    }
}
