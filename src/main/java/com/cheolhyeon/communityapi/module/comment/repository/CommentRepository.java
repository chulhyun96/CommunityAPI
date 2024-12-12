package com.cheolhyeon.communityapi.module.comment.repository;

import com.cheolhyeon.communityapi.module.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
   @Query("SELECT c FROM Comment c WHERE c.post.id = :id")
   List<Comment> findByPostId(Long id);

}
