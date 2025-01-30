package com.cheolhyeon.communityapi.module.post.entity;

import com.cheolhyeon.communityapi.common.exception.BaseEntity;
import com.cheolhyeon.communityapi.module.auth.entity.Users;
import com.cheolhyeon.communityapi.module.comment.entity.Comment;
import com.cheolhyeon.communityapi.module.post.dto.PostRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private final List<Comment> comments = new ArrayList<>();

    @Length(max = 255)
    private String title;

    @Lob
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
    public void addComment(Comment comment) {
        this.comments.add(comment);
        this.commentCount = this.comments.size();
        comment.assignPost(this);
    }

    public String getWriter() {
        return getUser().getUsername();
    }
}
