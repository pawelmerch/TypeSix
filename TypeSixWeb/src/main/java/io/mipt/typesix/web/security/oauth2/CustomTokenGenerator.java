package io.mipt.typesix.web.security.oauth2;

import io.mipt.typesix.businesslogic.service.core.api.AuthenticationService;
import io.mipt.typesix.web.security.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

@Configuration
@RequiredArgsConstructor
public class CustomTokenGenerator {
    private final AuthenticationService authenticationService;

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer() {
        return (context) -> {
            String email = Utils.retrieveEmailFromAuthentication(context.getPrincipal());
            authenticationService.onTokenCreate(email, (name, value) -> {
                context.getClaims().claim(name, value);
            });
        };
    }
}