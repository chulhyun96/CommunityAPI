package com.cheolhyeon.communityapi.module.auth.security.jwt;

import com.cheolhyeon.communityapi.module.auth.dto.AuthRequest;
import com.cheolhyeon.communityapi.module.auth.dto.CustomUserDetails;
import com.cheolhyeon.communityapi.module.auth.dto.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
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

import static com.cheolhyeon.communityapi.module.auth.security.SecurityConfig.*;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper;
    private final JWTProvider jwtProvider;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        AuthRequest authRequest = getAuthRequest(request);

        String username = authRequest.getUsername();
        String password = authRequest.getPassword();

        log.info("Attempting to authenticate user: {}", username);
        log.info("Attempting to authenticate password: {}", password);

        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        CustomUserDetails principal = (CustomUserDetails) authResult.getPrincipal();
        String token = jwtProvider.generateToken(principal.getUsername(), getRole(authResult));
        response.addHeader(AUTHORIZATION_HEADER,"Bearer " + token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(
                ErrorResponse.create(HttpStatus.BAD_REQUEST,"계정이 존재하지 않습니다.")
        ));
    }

    private static String getRole(Authentication authResult) {
        return authResult.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findAny()
                .orElseThrow(() -> new AccessDeniedException("해당 유저는 권한이 존재하지 않습니다."));
    }

    private AuthRequest getAuthRequest(HttpServletRequest request) {
        try {
            return objectMapper.readValue(request.getInputStream(), AuthRequest.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
