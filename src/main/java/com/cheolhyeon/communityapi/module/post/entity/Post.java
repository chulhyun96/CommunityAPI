package com.cheolhyeon.communityapi.module.post.entity;

import com.cheolhyeon.communityapi.module.auth.entity.Users;
import com.cheolhyeon.communityapi.module.common.BaseEntity;
import com.cheolhyeon.communityapi.module.post.dto.PostRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_Id")
    private Users user;

    private String title;
    private String content;
    private Integer commentCount;

    private Post(PostRequest postRequest) {
        this.title = postRequest.getTitle();
        this.content = postRequest.getContent();
        this.commentCount = 0;
    }

    public static Post create(PostRequest postRequest) {
        return new Post(postRequest);
    }

    public void assignUser(Users users) {
        this.user = users;
    }

}
