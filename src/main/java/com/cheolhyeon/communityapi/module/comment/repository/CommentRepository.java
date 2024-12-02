package com.cheolhyeon.communityapi.module.comment.repository;

import com.cheolhyeon.communityapi.module.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
   List<Comment> findCommentsByUserId(Long userId);
}
