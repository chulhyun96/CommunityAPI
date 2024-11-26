package com.cheolhyeon.communityapi.module.auth.security.jwt;

import com.cheolhyeon.communityapi.module.auth.dto.CustomUserDetails;
import com.cheolhyeon.communityapi.module.auth.dto.error.ErrorResponse;
import com.cheolhyeon.communityapi.module.auth.entity.Users;
import com.cheolhyeon.communityapi.module.auth.type.AuthErrorStatus;
import com.cheolhyeon.communityapi.module.auth.type.AuthorityPolicy;
import com.cheolhyeon.communityapi.module.auth.util.JsonPrettyPrinter;
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
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.cheolhyeon.communityapi.module.auth.security.SecurityConfig.AUTHORIZATION_HEADER;


@Slf4j
@RequiredArgsConstructor
public class JWTPerRequestFilter extends OncePerRequestFilter {
    private static final String LOGIN_URL = "/login";
    private static final String SIGNUP_URL_USER = "/signup/user";
    private static final String SIGNUP_URL_ADMIN = "/signup/admin";
    private static final String HOME = "/";

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
        return StringUtils.pathEquals(path, LOGIN_URL) ||
                StringUtils.pathEquals(path, SIGNUP_URL_USER) ||
                StringUtils.pathEquals(path, SIGNUP_URL_ADMIN) ||
                StringUtils.pathEquals(path, HOME);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        TokenPolicy policy = TokenPolicy.getTokenPolicy(bearerToken, jwtProvider);
        String token = policy.apply(bearerToken);

        if (!StringUtils.hasText(token)) {
            handleTokenInvalid(response);
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

    private void handleTokenInvalid(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setHeader(HttpHeaders.LOCATION, "/login");
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());

        String prettyForPrint = JsonPrettyPrinter.getPrettyForPrint(objectMapper,
                ErrorResponse.create(AuthErrorStatus.TOKEN_EXPIRED));

        response.getWriter().write(prettyForPrint);
    }
}
