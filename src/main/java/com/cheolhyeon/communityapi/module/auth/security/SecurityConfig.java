package com.cheolhyeon.communityapi.module.auth.security;

import com.cheolhyeon.communityapi.module.auth.security.jwt.JWTPerRequestFilter;
import com.cheolhyeon.communityapi.module.auth.security.jwt.JWTProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String[] PUBLIC_URIS = {"/", "/login", "/signup/**", "/post/{id}"};

    /*React localHost 주소*/
    protected static final String HTTP_LOCALHOST_3000 = "http://localhost:3000";

    private static final String ROOT_PATH_PATTERN = "*";
    private static final String[] ADMIN_URI = {"/admin/**"};

    private final AuthenticationConfiguration configuration;
    private final ObjectMapper objectMapper;
    private final JWTProvider jwtProvider;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        applyCorsPolicy(http);
        applyDisablePolicy(http);
        applySessionPolicy(http);
        authorizeHttpRequestMatchers(http);

        addFilter(http, JWTPerRequestFilter.create(jwtProvider, objectMapper),
                CustomAuthenticationFilter.create(authenticationManager(configuration),
                        objectMapper, jwtProvider)
        );

        return http.build();
    }

    private void addFilter(HttpSecurity http,
                           JWTPerRequestFilter jwtPerRequestFilter, CustomAuthenticationFilter customAuthenticationFilter) {
        http.addFilterBefore(jwtPerRequestFilter, CustomAuthenticationFilter.class);
        http.addFilterAt(customAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    private void authorizeHttpRequestMatchers(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .requestMatchers(PUBLIC_URIS).permitAll()
                .requestMatchers(ADMIN_URI).hasRole("ADMIN")
                .anyRequest().authenticated()
        );
    }

    private void applyDisablePolicy(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.formLogin(AbstractHttpConfigurer::disable);
        http.logout(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);
    }

    private void applySessionPolicy(HttpSecurity http) throws Exception {
        http.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    }

    private void applyCorsPolicy(HttpSecurity http) throws Exception {
        http.cors(cors -> cors
                .configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration configuration = new CorsConfiguration();
                        configuration.setAllowCredentials(true);
                        configuration.setAllowedHeaders(Collections.singletonList(AUTHORIZATION_HEADER));
                        configuration.setAllowedMethods(Collections.singletonList(ROOT_PATH_PATTERN));
                        configuration.setAllowedOrigins(Collections.singletonList(HTTP_LOCALHOST_3000));
                        configuration.setMaxAge(3600L);
                        configuration.setExposedHeaders(Collections.singletonList(AUTHORIZATION_HEADER));
                        return configuration;
                    }
                }));
    }
}
