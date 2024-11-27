package com.cheolhyeon.communityapi.module.auth.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
public class JWTProvider {
    private static final long TOKEN_EXPIRATION_TIME = 60 * 60 * 1000;
    private static final String ALGORITHM = "HmacSHA256";
    private final SecretKey secretKey;

    public JWTProvider(@Value("${jwt.secret}") String tokenKey) {
        this.secretKey = new SecretKeySpec(tokenKey.getBytes(
                StandardCharsets.UTF_8),
                ALGORITHM);
    }

    public String getUsername(String token) {
        try {
            return Jwts.parser().verifyWith(secretKey).build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .get("username", String.class);
        } catch (JwtException e) {
            log.error(e.getMessage());
            return "ERROR";
        }
    }

    public String getRoleAsString(String token) {
        try {
            return Jwts.parser().verifyWith(secretKey).build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .get("role", String.class);
        } catch (JwtException e) {
            log.error(e.getMessage());
            return "ERROR";
        }
    }

    public boolean isExpired(String token) {
        try {
            return Jwts.parser().verifyWith(secretKey).build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getExpiration()
                    .before(new Date());
        } catch (ExpiredJwtException e) {
            log.error(e.getMessage());
            return true;
        }
    }

    public String generateToken(String username, String role) {
        return Jwts.builder()
                .claim("username", username)
                .claim("role", role)
                .issuedAt(getCurrentDate())
                .expiration(getExpirationDate())
                .signWith(secretKey)
                .compact();
    }

    private static Date getExpirationDate() {
        return new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME);
    }

    private static Date getCurrentDate() {
        return new Date(System.currentTimeMillis());
    }
}
