package com.cheolhyeon.communityapi.module.auth.security;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static com.cheolhyeon.communityapi.module.auth.security.SecurityConst.*;

@Configurable
public class CorsMvcConfig implements WebMvcConfigurer {
    private static final String ALL_PATH_PATTERN = "/**";

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping(ALL_PATH_PATTERN)
                .allowedOrigins(HTTP_LOCALHOST_3000);
    }
}
