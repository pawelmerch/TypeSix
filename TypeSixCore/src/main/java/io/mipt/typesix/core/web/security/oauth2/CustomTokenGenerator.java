package io.mipt.typesix.core.web.security.oauth2;

import io.micrometer.core.instrument.Counter;
import io.mipt.typesix.businesslogic.service.core.AuthenticationService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

import static io.mipt.typesix.core.utils.Utils.retrieveEmail;

@Configuration
public class CustomTokenGenerator {
    private final AuthenticationService authenticationService;
    private final Counter loginCounter;

    public CustomTokenGenerator(AuthenticationService authenticationService, @Qualifier("login_counter") Counter loginCounter) {
        this.authenticationService = authenticationService;
        this.loginCounter = loginCounter;
    }

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer() {
        return (context) -> {
            String email = retrieveEmail(context.getPrincipal());
            authenticationService.customizeToken(email, (name, value) -> {
                context.getClaims().claim(name, value);
            });
            loginCounter.increment();
        };
    }
}