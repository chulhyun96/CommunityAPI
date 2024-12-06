package com.cheolhyeon.communityapi.module.auth.dto.user;

import com.cheolhyeon.communityapi.module.auth.entity.Users;
import com.cheolhyeon.communityapi.module.auth.type.AuthorityPolicy;
import lombok.Getter;

@Getter
public class MyInfoResponse {
    private final String username;
    private final String phoneNumber;
    private final AuthorityPolicy role;
    private final Integer countOfPosts;
    private final Integer countOfComments;

    private MyInfoResponse(Users user) {
        this.username = user.getUsername();
        this.phoneNumber = maskPhoneNumber(user.getPhoneNumber());
        this.role = user.getRole();
        this.countOfPosts = user.getPosts().size();
        this.countOfComments = user.getComments().size();
    }

    public static MyInfoResponse create(Users user) {
        return new MyInfoResponse(user);
    }

    private String maskPhoneNumber(String phoneNumber) {
        String[] parts = phoneNumber.split("-");
        return parts[0] + "-" + parts[1] + "-" + "****";
    }
}
