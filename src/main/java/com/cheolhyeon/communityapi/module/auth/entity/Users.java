package com.cheolhyeon.communityapi.module.auth.entity;

import com.cheolhyeon.communityapi.module.auth.type.AuthorityPolicy;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Users extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10, unique = true)
    private String username;

    @Column(nullable = false, length = 10)
    private String password;

    @Column(nullable = false, length = 15)
    private String phoneNumber;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthorityPolicy role;
}
