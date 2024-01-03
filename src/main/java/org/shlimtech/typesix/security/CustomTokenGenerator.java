package org.shlimtech.typesix.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

@Configuration
public class CustomTokenGenerator {

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer() {
        return (context) -> {
            // TODO retrieve user id from user name here, then save it into token claims
            OAuth2AuthenticationToken token = context.getPrincipal();
            OAuth2User user = token.getPrincipal();
            String email = user.getName();
            context.getClaims().claim("email", email);
        };
    }

}