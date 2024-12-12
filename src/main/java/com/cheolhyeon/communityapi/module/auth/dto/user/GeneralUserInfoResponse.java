package com.cheolhyeon.communityapi.module.auth.dto.user;

import lombok.Getter;

@Getter
public class GeneralUserInfoResponse {
    private final String username;
    private final Long countOfPosts;
    private final Long countOfComments;

    private GeneralUserInfoResponse(String username, Long countOfPosts, Long countOfComments) {
        this.username = username;
        this.countOfPosts = countOfPosts;
        this.countOfComments = countOfComments;
    }

    public static GeneralUserInfoResponse create(String username, Long countOfPosts, Long countOfComments) {
        return new GeneralUserInfoResponse(username, countOfPosts, countOfComments);
    }
}
