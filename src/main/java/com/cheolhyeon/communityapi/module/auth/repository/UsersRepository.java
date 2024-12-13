package com.cheolhyeon.communityapi.module.auth.repository;

import com.cheolhyeon.communityapi.module.auth.dto.user.GeneralUserInfoResponse;
import com.cheolhyeon.communityapi.module.auth.dto.user.MyInfoResponse;
import com.cheolhyeon.communityapi.module.auth.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(String username);

    Optional<Users> findById(Long id);

    @Query("""
            SELECT new com.cheolhyeon.communityapi.module.auth.dto.user.MyInfoResponse(
            u.username, 
            u.phoneNumber, 
            u.role, 
            (SELECT count(p) FROM Post p WHERE p.user = u), 
            (SELECT count(c) FROM Comment c WHERE c.user = u))
            FROM Users u 
            WHERE u.username = :username
            """)
    Optional<MyInfoResponse> findMyInfoByUsernameWithPostCommentDto(String username);

    @Query("""
            SELECT new com.cheolhyeon.communityapi.module.auth.dto.user.GeneralUserInfoResponse(
            u.username, 
            (SELECT count(p) FROM Post p WHERE p.user = u), 
            (SELECT count(c) FROM Comment c WHERE c.user = u))
            FROM Users u 
            WHERE u.username = :username
            """)
    Optional<GeneralUserInfoResponse> findUserInfoByUsernameWithPostCommentDto(String username);
}
