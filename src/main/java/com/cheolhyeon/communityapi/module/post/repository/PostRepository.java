package com.cheolhyeon.communityapi.module.post.repository;

import com.cheolhyeon.communityapi.module.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
