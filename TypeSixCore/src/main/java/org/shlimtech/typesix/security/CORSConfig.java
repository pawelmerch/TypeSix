package org.shlimtech.typesix.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class CORSConfig implements WebMvcConfigurer {

    @Value("${type-6.client-cors-allowed-origin}")
    private String clientAllowedCorsOrigin;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // TO FIX CORS ERROR
        registry.addMapping("/**").allowedOrigins(clientAllowedCorsOrigin);
    }

}