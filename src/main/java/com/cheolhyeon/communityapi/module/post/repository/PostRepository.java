package com.cheolhyeon.communityapi.module.post.repository;

import com.cheolhyeon.communityapi.module.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("""
                SELECT p FROM Post p
                JOIN FETCH p.user
                LEFT JOIN FETCH p.comments c
                LEFT JOIN FETCH c.user
                WHERE p.id = :id
            """)
    Optional<Post> findPostWithCommentsAndUsers(Long id);


    @Query(value = """
                SELECT p.*, COUNT(c.id) AS commentCount
                FROM post p
                LEFT JOIN comment c ON p.id = c.post_id
                GROUP BY p.id
                ORDER BY commentCount DESC
                LIMIT :limit OFFSET :offset
            """, nativeQuery = true)
    List<Post> findAllSortedByCommentsCount(@Param("limit") int limit, @Param("offset") int offset);
}
