package com.cheolhyeon.communityapi.module.auth.security.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.function.Function;

@Slf4j
public enum TokenPolicy {
    BEARER_TOKEN(TokenPolicy::getToken),
    NONE(TokenPolicy::getNone);

    private final Function<String, String> function;

    TokenPolicy(Function<String, String> function) {
        this.function = function;
    }

    public static TokenPolicy getTokenPolicy(String bearerToken, JWTProvider jwtProvider) {
        if (isInvalidToken(bearerToken)) {
            log.info("Bearer token is Invalid");
            return NONE;
        }
        String token = getToken(bearerToken);
        if(jwtProvider.isExpired(token)) {
            log.info("Bearer token is Expired");
            return NONE;
        }
        return BEARER_TOKEN;
    }
    public String apply(String input) {
        return function.apply(input);
    }

    private static boolean isInvalidToken(String bearerToken) {
        return !StringUtils.hasText(bearerToken) || !StringUtils.startsWithIgnoreCase(bearerToken, "Bearer");
    }

    private static String getToken(String bearerToken) {
        return bearerToken.substring(7);
    }

    private static String getNone(String s) {
        return "";
    }
}
