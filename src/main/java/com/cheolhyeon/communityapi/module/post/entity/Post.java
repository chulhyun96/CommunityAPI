package com.cheolhyeon.communityapi.module.post.entity;

import com.cheolhyeon.communityapi.module.auth.entity.BaseEntity;
import com.cheolhyeon.communityapi.module.auth.entity.Users;
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

    public void assignUser(Users users) {
        this.user = users;
    }
}
