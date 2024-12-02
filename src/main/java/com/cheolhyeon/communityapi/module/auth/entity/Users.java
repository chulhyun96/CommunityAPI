package com.cheolhyeon.communityapi.module.auth.entity;

import com.cheolhyeon.communityapi.module.auth.dto.auth.AuthRequest;
import com.cheolhyeon.communityapi.module.auth.type.AuthorityPolicy;
import com.cheolhyeon.communityapi.module.comment.entity.Comment;
import com.cheolhyeon.communityapi.common.BaseEntity;
import com.cheolhyeon.communityapi.module.post.entity.Post;
import jakarta.persistence.*;
import lombok.*;

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

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @OrderBy("id desc")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user",  cascade = CascadeType.REMOVE)
    @OrderBy("id desc")
    private List<Comment> comments = new ArrayList<>();

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
        posts.add(post);
        post.assignUser(this);
    }
    public void addComment(Comment comment) {
        comments.add(comment);
        comment.assignUser(this);
    }

    public String getRoleAsString() {
        return role.toString();
    }

}
