package com.cheolhyeon.communityapi.module.auth.dto.user;

import com.cheolhyeon.communityapi.module.auth.entity.Users;
import com.cheolhyeon.communityapi.module.auth.type.AuthorityPolicy;
import lombok.Getter;

@Getter
public class MyInfoResponse {
    private final String username;
    private final String phoneNumber;
    private final AuthorityPolicy role;
    private final Long countOfPosts;
    private final Long countOfComments;

    private MyInfoResponse(String username, String phoneNumber,
                           AuthorityPolicy role, Long countOfPosts,
                           Long countOfComments) {
        this.username = username;
        this.phoneNumber = maskPhoneNumber(phoneNumber);
        this.role = role;
        this.countOfPosts = countOfPosts;
        this.countOfComments = countOfComments;
    }

    public static MyInfoResponse create(String username, String phoneNumber,
                                        AuthorityPolicy role, Long countOfPosts,
                                        Long countOfComments) {
        return new MyInfoResponse(username, phoneNumber,
                role, countOfPosts, countOfComments);
    }

    private String maskPhoneNumber(String phoneNumber) {
        String[] parts = phoneNumber.split("-");
        return parts[0] + "-" + parts[1] + "-" + "****";
    }
}
