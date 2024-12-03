package com.cheolhyeon.communityapi.module.auth.security.jwt;

import com.cheolhyeon.communityapi.module.auth.dto.CustomUserDetails;
import com.cheolhyeon.communityapi.module.auth.dto.error.HandleAuthErrorFactory;
import com.cheolhyeon.communityapi.module.auth.entity.Users;
import com.cheolhyeon.communityapi.module.auth.type.AuthErrorStatus;
import com.cheolhyeon.communityapi.module.auth.type.AuthorityPolicy;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static com.cheolhyeon.communityapi.module.auth.security.SecurityConfig.*;


@Slf4j
@RequiredArgsConstructor
public class JWTPerRequestFilter extends OncePerRequestFilter {
    private final JWTProvider jwtProvider;
    private final ObjectMapper objectMapper;

    public static JWTPerRequestFilter create(JWTProvider jwtProvider, ObjectMapper objectMapper) {
        return new JWTPerRequestFilter(jwtProvider, objectMapper);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return isPathEquals(path);
    }

    private boolean isPathEquals(String path) {
        return publicUris.stream()
                .anyMatch(publicUri -> new AntPathMatcher().match(publicUri, path));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (!StringUtils.hasText(bearerToken)) {
            handleAuthorInvalid(response, AuthErrorStatus.LOGIN_FIRST);
            return;
        }

        TokenPolicy policy = TokenPolicy.getTokenPolicy(bearerToken, jwtProvider);
        String token = policy.apply(bearerToken);

        if (!StringUtils.hasText(token)) {
            handleAuthorInvalid(response, AuthErrorStatus.TOKEN_EXPIRED);
            return;
        }

        Authentication auth = createAuthentication(token);

        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request, response);
    }

    private Authentication createAuthentication(String token) {
        String username = jwtProvider.getUsername(token);
        AuthorityPolicy role = AuthorityPolicy.fromString(jwtProvider.getRoleAsString(token));

        CustomUserDetails customUser = CustomUserDetails.create(
                Users.builder()
                        .username(username)
                        .role(role)
                        .build()
        );

        return new UsernamePasswordAuthenticationToken(customUser, null, customUser.getAuthorities());
    }

    private void handleAuthorInvalid(HttpServletResponse response, AuthErrorStatus errorStatus) throws IOException {
        setErrorHeaderInfo(response);
        String errorResponseAsJson = HandleAuthErrorFactory.getErrorResponse(objectMapper, errorStatus);
        response.getWriter().write(errorResponseAsJson);
    }

    private void setErrorHeaderInfo(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setHeader(HttpHeaders.LOCATION, "/login");
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
    }
}
