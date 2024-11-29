package com.cheolhyeon.communityapi.module.auth.dto.user;

import com.cheolhyeon.communityapi.module.auth.entity.Users;
import com.cheolhyeon.communityapi.module.auth.type.AuthorityPolicy;
import com.cheolhyeon.communityapi.module.post.entity.Post;
import lombok.Getter;

import java.util.List;

@Getter
public class UserResponse {
    private final String username;
    private final String phoneNumber;
    private final AuthorityPolicy role;
    private final Integer countOfPosts;
    private final Integer countOfComments;

    private UserResponse(Users user) {
        this.username = user.getUsername();
        this.phoneNumber = maskPhoneNumber(user.getPhoneNumber());
        this.role = user.getRole();
        this.countOfPosts = user.getPosts().size();
        this.countOfComments = user.getComments().size();
    }

    public static UserResponse create(Users user) {
        return new UserResponse(user);
    }

    private String maskPhoneNumber(String phoneNumber) {
        String[] parts = phoneNumber.split("-");
        return parts[0] + "-" + parts[1] + "-" + "****";
    }
}
