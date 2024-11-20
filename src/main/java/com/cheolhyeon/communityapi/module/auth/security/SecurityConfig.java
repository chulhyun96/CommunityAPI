package com.cheolhyeon.communityapi.module.auth.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

import static com.cheolhyeon.communityapi.module.auth.security.SecurityConst.HTTP_LOCALHOST_3000;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private static final String ROOT_PATH_PATTERN = "*";
    private static final String[] PUBLIC_URIS = {"/", "/login", "/signup"};
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String[] ADMIN_URI = {"/admin/**"};


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.formLogin(AbstractHttpConfigurer::disable);
        http.logout(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);

        http.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.cors(cors -> cors
                .configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration configuration = new CorsConfiguration();
                        configuration.setAllowCredentials(true);
                        configuration.setAllowedHeaders(Collections.singletonList(ROOT_PATH_PATTERN));
                        configuration.setAllowedMethods(Collections.singletonList(ROOT_PATH_PATTERN));
                        configuration.setAllowedOrigins(Collections.singletonList(HTTP_LOCALHOST_3000));
                        configuration.setMaxAge(3600L);
                        configuration.setExposedHeaders(Collections.singletonList(AUTHORIZATION_HEADER));
                        return configuration;
                    }
                }));

        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .requestMatchers(PUBLIC_URIS).permitAll()
                .requestMatchers(ADMIN_URI).hasRole("ADMIN")
                .anyRequest().authenticated()
        );
        return http.build();
    }

}
