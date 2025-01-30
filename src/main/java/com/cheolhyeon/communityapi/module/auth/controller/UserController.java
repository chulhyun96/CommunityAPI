package com.cheolhyeon.communityapi.module.auth.controller;

import com.cheolhyeon.communityapi.module.auth.dto.CustomUserDetails;
import com.cheolhyeon.communityapi.module.auth.dto.user.GeneralUserInfoResponse;
import com.cheolhyeon.communityapi.module.auth.dto.user.MyInfoResponse;
import com.cheolhyeon.communityapi.module.auth.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
        MyInfoResponse myInfo = userService.getMyInfo(user.getUsername());
        return ResponseEntity.ok(myInfo);
    }

    /**
     * 타인 정보 보기
     */
    @GetMapping("/user/{username}")
    public ResponseEntity<?> getUserInfoDetail(@PathVariable("username") String username) {
        GeneralUserInfoResponse userInfo = userService.getUserInfo(username);
        return ResponseEntity.ok(userInfo);
    }
    /**
     * 내 정보 변경
     */
    /*@PatchMapping("/user")
    public ResponseEntity<?> updateUser(Authentication authentication,
                                        @RequestBody UpdateMyInfoRequest request) {
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(userService.updateMyInfo(user.getUsername(),request));
    }*/
}
