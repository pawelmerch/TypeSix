package org.shlimtech.typesix.security;

import io.micrometer.core.instrument.Counter;
import org.shlimtech.typesixdatabasecommon.dto.UserDTO;
import org.shlimtech.typesixdatabasecommon.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

@Configuration
public class CustomTokenGenerator {

    private final UserService userService;
    private final Counter loginCounter;

    public CustomTokenGenerator(UserService userService, @Qualifier("login_counter") Counter loginCounter) {
        this.userService = userService;
        this.loginCounter = loginCounter;
    }


    @Bean
    @Profile("release")
    public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer() {
        return (context) -> {
            OAuth2AuthenticationToken token = context.getPrincipal();
            OAuth2User user = token.getPrincipal();
            String email = user.getName();
            UserDTO userDTO = userService.loadUser(email);
            loginCounter.increment();
            context.getClaims().claim("email", userDTO.getEmail()).claim("id", userDTO.getId());
        };
    }

}