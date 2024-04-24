package org.shlimtech.typesix.security;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.extern.java.Log;
import org.shlimtech.typesix.utils.JwkUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import java.util.UUID;
import java.util.stream.Collectors;

import static org.shlimtech.typesix.security.EndpointsList.*;

@EnableWebSecurity
@Configuration
@Log
@EnableScheduling
public class SecurityConfig {

    public SecurityConfig(@Value("${spring.profiles.active}") String activeProfile,
                          @Value("${type-6.selfUrl}") String selfUrl,
                          @Autowired OAuth2ClientProperties oAuth2ClientProperties) {
        setRedirectUriToAllRegistrations(oAuth2ClientProperties, selfUrl);
    }

    @Bean
    @Order(1)
    public SecurityFilterChain authServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer =
                new OAuth2AuthorizationServerConfigurer();
        return http
                .securityMatcher(OAUTH2_TOKEN_ENDPOINT, OAUTH2_AUTHORIZATION_ENDPOINT)
                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
                .csrf(AbstractHttpConfigurer::disable)
                .apply(authorizationServerConfigurer).and()
                .exceptionHandling(exceptions ->
                        exceptions.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint(LOGIN_ENDPOINT))
                ).build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        // security chain for ui purposes
        return http
                // TODO restore CSRF in login page
                .csrf(AbstractHttpConfigurer::disable)
                // indicates that this chain is used only with /login and /logout URLs
                .securityMatcher(
                        LOGIN_ENDPOINT,
                        LOGOUT_ENDPOINT,
                        THIRD_PARTY_AUTHORIZATION_ENDPOINT + "/*",
                        THIRD_PARTY_CODE_ENDPOINT + "/*",
                        FORM_LOGIN_ENDPOINT,
                        SUCCESS_LOGIN_PAGE,
                        ERROR_PAGE
                )
                // only /login and /logout URLs are permitted, all others are denied
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(LOGIN_ENDPOINT, ERROR_PAGE).permitAll()
                        .anyRequest().authenticated())
                // form login authentication filter is enabled
                .formLogin(c -> c.loginPage(LOGIN_ENDPOINT).loginProcessingUrl(FORM_LOGIN_ENDPOINT).successForwardUrl(SUCCESS_LOGIN_PAGE))
                // oauth2 login authentication filter is enabled
                .oauth2Login(c -> c
                        .loginPage(LOGIN_ENDPOINT)
                        .defaultSuccessUrl(SUCCESS_LOGIN_PAGE)
                        .authorizationEndpoint(endpoint -> endpoint.baseUri(THIRD_PARTY_AUTHORIZATION_ENDPOINT))
                        .redirectionEndpoint(endpoint -> endpoint.baseUri(THIRD_PARTY_CODE_ENDPOINT + "/*"))
                )
                // default logout filter is disabled
                .logout(LogoutConfigurer::disable)
                .build();
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
                                .build()
                ).collect(Collectors.toList()));
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder()
                .tokenEndpoint(OAUTH2_TOKEN_ENDPOINT)
                .authorizationEndpoint(OAUTH2_AUTHORIZATION_ENDPOINT)
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

    private void setRedirectUriToAllRegistrations(OAuth2ClientProperties oAuth2ClientProperties, String selfUrl) {
        oAuth2ClientProperties.getRegistration().forEach((key, value) -> value.setRedirectUri(selfUrl + THIRD_PARTY_CODE_ENDPOINT + "/" + key));
    }

}