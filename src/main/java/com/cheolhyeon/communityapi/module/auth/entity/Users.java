package com.cheolhyeon.communityapi.module.auth.entity;

import com.cheolhyeon.communityapi.module.auth.dto.auth.AuthRequest;
import com.cheolhyeon.communityapi.module.auth.type.AuthorityPolicy;
import com.cheolhyeon.communityapi.module.post.entity.Post;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Users extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 15)
    private String phoneNumber;

    @OneToMany(mappedBy = "user")
    private final List<Post> posts = new ArrayList<>();

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthorityPolicy role;

    private Users(AuthRequest request, String encodedPassword, AuthorityPolicy role) {
        this.username = request.getUsername();
        this.password = encodedPassword;
        this.phoneNumber = request.getPhoneNumber();
        this.role = role;
    }

    public static Users create(AuthRequest request, String encodedPassword, AuthorityPolicy role) {
        return new Users(request, encodedPassword, role);
    }

    public void addPost(Post post) {
        post.assignUser(this);
        posts.add(post);
    }

    public String getRoleAsString() {
        return role.toString();
    }
}
