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

    @Query("select new com.cheolhyeon.communityapi.module.auth.dto.user.MyInfoResponse(" +
            "u.username, " +
            "u.phoneNumber, " +
            "u.role, " +
            "(select count(p) from Post p where p.user = u), " +
            "(select count(c) from Comment c where c.user = u)) " +
            "from Users u " +
            "where u.username = :username")
    Optional<MyInfoResponse> findMyInfoByUsernameWithPostCommentDto(String username);

    Optional<GeneralUserInfoResponse> findUserInfoByUsernameWithPostCommentDto(String username);
}
