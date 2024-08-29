package io.mipt.typesix.web.security.config;

import io.mipt.typesix.web.security.oauth2.Type6Oauth2ClientProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class CorsConfig implements WebMvcConfigurer {
    private final Type6Oauth2ClientProperties clientProperties;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // TO FIX CORS ERROR
        registry.addMapping("/**").allowedOrigins(clientProperties
                .getClients()
                .values()
                .stream()
                .map(Type6Oauth2ClientProperties.Type6Oauth2Client::getClientHostname)
                .toArray(String[]::new));
    }
}