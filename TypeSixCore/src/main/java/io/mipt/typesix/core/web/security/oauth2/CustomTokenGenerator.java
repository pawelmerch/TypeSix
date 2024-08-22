package io.mipt.typesix.core.web.security.oauth2;

import io.micrometer.core.instrument.Counter;
import io.mipt.typesix.businesslogic.service.core.AuthenticationService;
import io.mipt.typesix.core.web.security.form.CustomUserPrinciple;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.util.Assert;

@Configuration
public class CustomTokenGenerator {
    private final AuthenticationService authenticationService;
    private final Counter loginCounter;

    public CustomTokenGenerator(
            AuthenticationService authenticationService,
            @Qualifier("login_counter") Counter loginCounter
    ) {
        this.authenticationService = authenticationService;
        this.loginCounter = loginCounter;
    }

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer() {
        return (context) -> {
            String email = retrieveEmailFromAuthentication(context.getPrincipal());
            authenticationService.customizeToken(email, (name, value) -> {
                context.getClaims().claim(name, value);
            });
            loginCounter.increment();
        };
    }

    private String retrieveEmailFromAuthentication(Authentication authentication) {
        Assert.notNull(authentication, "authentication must not be null");
        if (authentication instanceof OAuth2AuthenticationToken token) {
            OAuth2User user = token.getPrincipal();
            String email = user.getName();
            return email;
        } else if (authentication instanceof UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
            CustomUserPrinciple userPrinciple = (CustomUserPrinciple) usernamePasswordAuthenticationToken.getPrincipal();
            return userPrinciple.getUsername();
        }

        throw new IllegalStateException("Unsupported authentication type: " + authentication.getClass());
    }
}