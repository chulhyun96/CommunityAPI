package com.cheolhyeon.communityapi.module.auth.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static com.cheolhyeon.communityapi.module.auth.security.SecurityConfig.HTTP_LOCALHOST_3000;


@Configuration
public class CorsMvcConfig implements WebMvcConfigurer {
    private static final String ALL_PATH_PATTERN = "/**";

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping(ALL_PATH_PATTERN)
                .allowedOrigins(HTTP_LOCALHOST_3000);
    }
}
