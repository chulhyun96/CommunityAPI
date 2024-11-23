package com.cheolhyeon.communityapi.module.auth.security.jwt;

import com.cheolhyeon.communityapi.module.auth.dto.CustomUserDetails;
import com.cheolhyeon.communityapi.module.auth.entity.Users;
import com.cheolhyeon.communityapi.module.auth.type.AuthorityPolicy;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.cheolhyeon.communityapi.module.auth.security.SecurityConfig.AUTHORIZATION_HEADER;


@Slf4j
@RequiredArgsConstructor
public class JWTPerRequestFilter extends OncePerRequestFilter {
    private final JWTProvider jwtProvider;

    public static JWTPerRequestFilter create(JWTProvider jwtProvider) {
        return new JWTPerRequestFilter(jwtProvider);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (isInvalidToken(bearerToken)) {
            log.info("Bearer token is Invalid");
            filterChain.doFilter(request, response);
            return;
        }
        String token = getToken(bearerToken);
        if(jwtProvider.isExpired(token)) {
            log.info("Bearer token is Expired");
            handleExpiredToken(request, response);
            return;
        }
        Authentication auth = createAuthentication(token);

        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request, response);
    }

    private Authentication createAuthentication(String token) {
        String username = jwtProvider.getUsername(token);
        AuthorityPolicy role = AuthorityPolicy.fromString(jwtProvider.getRoleAsString(token));

        CustomUserDetails customUser = CustomUserDetails.from(
                Users.getAuthenticatedUser(username, role)
        );

        return new UsernamePasswordAuthenticationToken(customUser, null, customUser.getAuthorities());
    }

    private static String getToken(String bearerToken) {
        return bearerToken.substring(7);
    }

    private static void handleExpiredToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader("Location", "/login");
        response.getWriter().write("Token expired. Please log in.");
    }

    private static boolean isInvalidToken(String bearerToken) {
        return !StringUtils.hasText(bearerToken) || !StringUtils.startsWithIgnoreCase(bearerToken, "Bearer");
    }
}
