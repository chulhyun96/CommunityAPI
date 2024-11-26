package com.cheolhyeon.communityapi.module.auth.security;

import com.cheolhyeon.communityapi.module.auth.util.JsonPrettyPrinter;
import com.cheolhyeon.communityapi.module.auth.dto.auth.AuthRequest;
import com.cheolhyeon.communityapi.module.auth.dto.auth.AuthResponse;
import com.cheolhyeon.communityapi.module.auth.dto.CustomUserDetails;
import com.cheolhyeon.communityapi.module.auth.dto.error.ErrorResponse;
import com.cheolhyeon.communityapi.module.auth.security.jwt.JWTProvider;
import com.cheolhyeon.communityapi.module.auth.type.ErrorStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.cheolhyeon.communityapi.module.auth.security.SecurityConfig.*;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private static final String TOKEN_PREFIX = "Bearer ";

    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper;
    private final JWTProvider jwtProvider;

    public static CustomAuthenticationFilter create(
            AuthenticationManager authenticationManager,
            ObjectMapper objectMapper,
            JWTProvider jwtProvider
    ) {
        return new CustomAuthenticationFilter(authenticationManager, objectMapper, jwtProvider);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        AuthRequest authRequest = getAuthRequest(request);

        String username = authRequest.getUsername();
        String password = authRequest.getPassword();

        log.info("Attempting to authenticate user: {}", username);
        log.info("Attempting to authenticate password: {}", password);

        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        CustomUserDetails principal = (CustomUserDetails) authResult.getPrincipal();

        String prettyForPrint = JsonPrettyPrinter.getPrettyForPrint(objectMapper,
                AuthResponse.create(principal.getUsers()));

        String token = jwtProvider.generateToken(principal.getUsername(), getRole(authResult));

        response.addHeader(AUTHORIZATION_HEADER, getFormattedToken(token));
        response.getWriter().write(prettyForPrint);
    }

    private String getFormattedToken(String token) {
        return TOKEN_PREFIX + token;
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());

        String prettyForPrint = JsonPrettyPrinter.getPrettyForPrint(objectMapper, (
                ErrorResponse.of(ErrorStatus.ACCOUNT_NOT_EXISTS)
        ));

        response.getWriter().write(prettyForPrint);
    }

    private String getRole(Authentication authResult) {
        return authResult.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findAny()
                .orElseThrow(() -> new AccessDeniedException(ErrorStatus.ACCOUNT_DOESNT_HAVE_NOT_ROLE.getErrorType()));
    }

    private AuthRequest getAuthRequest(HttpServletRequest request) {
        try {
            return objectMapper.readValue(request.getInputStream(), AuthRequest.class);
        } catch (IOException e) {
            throw new RuntimeException(ErrorStatus.JSON_PARSING_EXCEPTION.getMessage());
        }
    }
}
