package org.shlimtech.typesix.security;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.extern.java.Log;
import org.shlimtech.typesix.utils.JwkUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import java.util.stream.Collectors;

@EnableWebSecurity
@Configuration
@Log
@EnableScheduling
public class SecurityConfig {

    private final boolean isDebug;

    public SecurityConfig(@Value("${spring.profiles.active}") String activeProfile) {
        isDebug = activeProfile.equals("debug");
    }

    @Bean
    @Order(1)
    public SecurityFilterChain authServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        return http.exceptionHandling(exceptions ->
                exceptions.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
        ).build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        HttpSecurity sec = http
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .authorizeHttpRequests(authorize -> authorize.requestMatchers("/login").permitAll().anyRequest().authenticated());

        if (isDebug) {
            sec.formLogin(Customizer.withDefaults()).logout(AbstractHttpConfigurer::disable); // TODO make login via creds not only in debug
        } else {
            sec.oauth2Login(c -> c.loginPage("/login")).logout(AbstractHttpConfigurer::disable); // TODO customize login page
        }

        return sec.build();
    }

    @Bean
    public WebSecurityCustomizer ignoringCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/actuator/**");
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository(Type6Oauth2ClientProperties clientProperties) {
        return new InMemoryRegisteredClientRepository(clientProperties
                .getClients()
                .values()
                .stream()
                .map(client ->
                        RegisteredClient
                                .withId(UUID.randomUUID().toString())
                                .clientId(client.getClientId())
                                .clientSecret("{noop}" + client.getClientSecret()) // TODO set encoding
                                .redirectUri(client.getClientRedirectUri())
                                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                                .tokenSettings(createTokenSettings())
                                .build()
                ).collect(Collectors.toList()));
    }


    @Bean
    public AuthorizationServerSettings authorizationServerSettings(@Value("${type-6.issuer}") String issuerIp) {
        return AuthorizationServerSettings.builder()
                .issuer(issuerIp)
                .build();
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        RSAKey rsaKey = JwkUtils.generateRsa();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

    private TokenSettings createTokenSettings() {
        var builder = TokenSettings.builder();

        if (isDebug) {
            builder.accessTokenTimeToLive(Duration.of(5, ChronoUnit.SECONDS));
        }

        return builder.build();
    }

}