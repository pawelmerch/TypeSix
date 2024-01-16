package org.shlimtech.typesix.security;

import lombok.RequiredArgsConstructor;
import org.shlimtech.typesixdatabasecommon.dto.UserDTO;
import org.shlimtech.typesixdatabasecommon.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

@Configuration
@RequiredArgsConstructor
public class CustomTokenGenerator {

    private final UserService userService;

    @Bean
    @Profile("release")
    public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer() {
        return (context) -> {
            OAuth2AuthenticationToken token = context.getPrincipal();
            OAuth2User user = token.getPrincipal();
            String email = user.getName();
            UserDTO userDTO = userService.loadUser(email);
            context.getClaims().claim("email", userDTO.getEmail()).claim("id", userDTO.getId());
        };
    }

}