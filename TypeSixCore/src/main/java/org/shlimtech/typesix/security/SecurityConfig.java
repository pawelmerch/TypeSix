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
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import java.util.UUID;

@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
@Log
@EnableScheduling
public class SecurityConfig {

    public SecurityConfig(
            @Value("${spring.security.oauth2.client.registration.github.clientId}") String githubClientId,
            @Value("${spring.security.oauth2.client.registration.github.clientSecret}") String githubClientSecret,
            @Value("${spring.security.oauth2.client.registration.yandex.clientId}") String yandexClientId,
            @Value("${spring.security.oauth2.client.registration.yandex.clientSecret}") String yandexClientSecret,
            @Value("${type-6.issuer}") String issuer,
            @Value("${type-6.client-id}") String clientId,
            @Value("${type-6.client-secret}") String clientSecret,
            @Value("${type-6.client-cors-allowed-origin}") String corsOrigin,
            @Value("${type-6.client-redirect-uri}") String redirectUri,
            @Value("${spring.datasource.url}") String databaseUrl,
            @Value("${spring.datasource.username}") String databaseUserName,
            @Value("${spring.datasource.password}") String databasePassword
    ) {
        log.info("githubClientId: [" + githubClientId + "]");
        log.info("githubClientSecret: [" + githubClientSecret + "]");
        log.info("yandexClientId: [" + yandexClientId + "]");
        log.info("yandexClientSecret: [" + yandexClientSecret + "]");
        log.info("issuer: [" + issuer + "]");
        log.info("clientId: [" + clientId + "]");
        log.info("clientSecret: [" + clientSecret + "]");
        log.info("corsOrigin: [" + corsOrigin + "]");
        log.info("redirectUri: [" + redirectUri + "]");
        log.info("databaseUser: [" + databaseUserName + "]");
        log.info("databasePassword: [" + databasePassword + "]");
        log.info("databaseUrl: [" + databaseUrl + "]");
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
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http,
            @Value("${spring.profiles.active}") String activeProfile) throws Exception {
        HttpSecurity sec = http
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated());

        if (activeProfile.equals("debug")) {
            sec.formLogin(Customizer.withDefaults()); // TODO make login via creds not only in debug
        } else {
            sec.oauth2Login(Customizer.withDefaults()); // TODO customize login page
        }

        return sec.build();
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository(
            @Value("${type-6.client-id}") String clientId,
            @Value("${type-6.client-secret}") String clientSecret,
            @Value("${type-6.client-redirect-uri}") String clientRedirectUri
    ) {
        return new InMemoryRegisteredClientRepository(
                RegisteredClient.withId(UUID.randomUUID().toString())
                        .clientId(clientId)
                        .clientSecret("{noop}" + clientSecret) // TODO set encoding
                        .redirectUri(clientRedirectUri)
                        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                        .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                        // TODO set to debug profile
                        //.tokenSettings(TokenSettings.builder()
                        //        .accessTokenTimeToLive(Duration.of(5, ChronoUnit.SECONDS))
                        //        .build())
                        .build()
        );
    }


    @Bean
    public AuthorizationServerSettings authorizationServerSettings(
            @Value("${spring.security.oauth2.client.registration.github.clientId}") String githubClientId,
            @Value("${spring.security.oauth2.client.registration.github.clientSecret}") String githubClientSecret,
            @Value("${type-6.issuer}") String issuerIp
            ) {
        log.info("[" + githubClientId + "], [" + githubClientSecret + "], [" + issuerIp + "]");
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

}