package com.cheolhyeon.communityapi.module.auth.repository;

import com.cheolhyeon.communityapi.module.auth.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(String username);

    @Query("SELECT u FROM Users u LEFT JOIN FETCH u.posts WHERE u.id = :id")
    Optional<Users> findByIdWithPosts(Long id);
}
