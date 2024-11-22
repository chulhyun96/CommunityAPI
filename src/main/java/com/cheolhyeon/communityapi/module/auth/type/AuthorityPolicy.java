package com.cheolhyeon.communityapi.module.auth.type;

public enum AuthorityPolicy {
    ROLE_ADMIN,ROLE_USER;

    public static AuthorityPolicy fromString(String roleAsString) {
        if (roleAsString.equalsIgnoreCase("ROLE_ADMIN")) {
            return ROLE_ADMIN;
        }
        return ROLE_USER;
    }
}
