package com.cheolhyeon.communityapi.module.auth.dto.user;

import com.cheolhyeon.communityapi.module.auth.entity.Users;
import lombok.Getter;

@Getter
public class GeneralUserInfoResponse {
    private final String username;
    private final int countOfPosts;
    private final int countOfComments;

    private GeneralUserInfoResponse(Users user) {
        this.username = user.getUsername();
        this.countOfPosts = user.getPosts().size();
        this.countOfComments = user.getComments().size();
    }
    public static GeneralUserInfoResponse create(Users user) {
        return new GeneralUserInfoResponse(user);
    }
}
