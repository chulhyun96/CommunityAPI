package com.cheolhyeon.communityapi.module.auth.repository;

import com.cheolhyeon.communityapi.module.auth.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(String username);

    Optional<Users> findById(Long id);
}
