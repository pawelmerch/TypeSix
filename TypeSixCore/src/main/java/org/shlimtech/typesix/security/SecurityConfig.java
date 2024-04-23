package org.shlimtech.typesix.security;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.shlimtech.typesix.utils.JwkUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
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
        configureDebugAccessDeniedHandler(http);
        return http.exceptionHandling(exceptions ->
                exceptions.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
        ).build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        configureDebugAccessDeniedHandler(http);
        // security chain for ui purposes
        return http
                // TODO restore CSRF in login page
                .csrf(AbstractHttpConfigurer::disable)
                // indicates that this chain is used only with /login and /logout URLs
                .securityMatcher("/login", "/logout", "/oauth2/authorization/**", "/login/oauth2/code/**")
                // only /login and /logout URLs are permitted, all others are denied
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/login", "/logout").permitAll()
                        .anyRequest().authenticated())
                // form login authentication filter is enabled
                .formLogin(c -> c.loginPage("/login"))
                // oauth2 login authentication filter is enabled
                .oauth2Login(c -> c.loginPage("/login"))
                // default logout filter is disabled
                .logout(LogoutConfigurer::disable)
                .build();
    }

    @Bean
    public WebSecurityCustomizer ignoringCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/actuator/**");
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository(Type6Oauth2ClientProperties clientProperties,
                                                                 PasswordEncoder passwordEncoder) {
        return new InMemoryRegisteredClientRepository(clientProperties
                .getClients()
                .values()
                .stream()
                .map(client ->
                        RegisteredClient
                                .withId(UUID.randomUUID().toString())
                                .clientId(client.getClientId())
                                .clientSecret(passwordEncoder.encode(client.getClientSecret()))
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

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    private TokenSettings createTokenSettings() {
        var builder = TokenSettings.builder();

        if (isDebug) {
            builder.accessTokenTimeToLive(Duration.of(5, ChronoUnit.SECONDS));
        }

        return builder.build();
    }

    @SneakyThrows
    private void configureDebugAccessDeniedHandler(HttpSecurity http) {
        if (isDebug) {
            http.exceptionHandling(exceptions -> exceptions.accessDeniedHandler((request, response, accessDeniedException) -> {
                accessDeniedException.printStackTrace();
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }));
        }
    }

}