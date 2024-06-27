package io.mipt.typesix.core.web.security.config;

import io.mipt.typesix.core.web.EndpointsList;
import io.mipt.typesix.core.web.security.oauth2.Type6Oauth2ClientProperties;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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

import java.util.stream.Collectors;

import static io.mipt.typesix.core.web.EndpointsList.*;

@Configuration
@Log
public class SecurityConfig {
    public SecurityConfig(@Value("${type-6.selfUrl}") String selfUrl,
                          @Autowired OAuth2ClientProperties oAuth2ClientProperties,
                          @Autowired WebEndpointProperties webEndpointProperties) {
        setRedirectUriToAllRegistrations(oAuth2ClientProperties, selfUrl);
        webEndpointProperties.setBasePath(ACTUATOR_BASE_PATH);
    }

    @Bean
    @Order(1)
    public SecurityFilterChain authServerSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                // only auth server endpoints
                .securityMatcher(
                        EndpointsList.OAUTH2_TOKEN_ENDPOINT,
                        EndpointsList.OAUTH2_AUTHORIZATION_ENDPOINT,
                        EndpointsList.OAUTH2_JWK_SET_ENDPOINT,
                        EndpointsList.TOKEN_INTROSPECTION_ENDPOINT
                )
                // all request require auth
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated()
                )
                // CSRF is off
                .csrf(AbstractHttpConfigurer::disable)
                // spring auth server config
                .apply(new OAuth2AuthorizationServerConfigurer()).and()
                // after session is created in pipeline start, user will be redirected to login page
                .exceptionHandling(exceptions ->
                        exceptions.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint(EndpointsList.LOGIN_PAGE))
                ).build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain basicSecurityFilterChain(HttpSecurity http) throws Exception {
        // basic security chain for most auth types
        return http
                // TODO restore CSRF in login page
                .csrf(AbstractHttpConfigurer::disable)
                // indicates that this chain is used only with /login and /logout URLs
                .securityMatcher(
                        LOGIN_PAGE,
                        LOGOUT_ENDPOINT,
                        THIRD_PARTY_AUTHORIZATION_ENDPOINT + "/*",
                        THIRD_PARTY_CODE_ENDPOINT + "/*",
                        FORM_LOGIN_ENDPOINT,
                        SUCCESS_LOGIN_PAGE,
                        ERROR_PAGE,
                        REGISTRATION_EMAIL_ENDPOINT,
                        REGISTRATION_CODE_ENDPOINT,
                        REGISTRATION_PASSWORD_SET_ENDPOINT,
                        REGISTRATION_EMAIL_PAGE,
                        REGISTRATION_CODE_PAGE,
                        REGISTRATION_PASSWORD_SET_PAGE
                )
                // login, logout, registration endpoints are permitted
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                LOGIN_PAGE,
                                LOGOUT_ENDPOINT,
                                FORM_LOGIN_ENDPOINT,
                                ERROR_PAGE,
                                REGISTRATION_EMAIL_ENDPOINT,
                                REGISTRATION_CODE_ENDPOINT,
                                REGISTRATION_PASSWORD_SET_ENDPOINT,
                                REGISTRATION_EMAIL_PAGE,
                                REGISTRATION_CODE_PAGE,
                                REGISTRATION_PASSWORD_SET_PAGE
                        ).permitAll()
                        .anyRequest().authenticated())
                // form login authentication filter is enabled
                .formLogin(c -> c.loginPage(LOGIN_PAGE).loginProcessingUrl(FORM_LOGIN_ENDPOINT).defaultSuccessUrl(SUCCESS_LOGIN_PAGE))
                // oauth2 login authentication filter is enabled
                .oauth2Login(c -> c
                        .loginPage(LOGIN_PAGE)
                        .defaultSuccessUrl(SUCCESS_LOGIN_PAGE)
                        .authorizationEndpoint(endpoint -> endpoint.baseUri(THIRD_PARTY_AUTHORIZATION_ENDPOINT))
                        .redirectionEndpoint(endpoint -> endpoint.baseUri(THIRD_PARTY_CODE_ENDPOINT + "/*"))
                )
                // default logout filter is disabled
                .logout(LogoutConfigurer::disable)
                .build();
    }

    @Bean
    @Order(3)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(authorize ->
                // expose actuator endpoints
                authorize.requestMatchers(
                        ACTUATOR_BASE_PATH + "/**",
                        SWAGGER_UI_BASE_PATH + "/**",
                        SPRING_DOC_PATH + "/**"
                ).permitAll().anyRequest().denyAll()
        ).build();
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
                                .withId(client.getClientId())
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
                .jwkSetEndpoint(OAUTH2_JWK_SET_ENDPOINT)
                .tokenIntrospectionEndpoint(TOKEN_INTROSPECTION_ENDPOINT)
                .build();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    private void setRedirectUriToAllRegistrations(OAuth2ClientProperties oAuth2ClientProperties, String selfUrl) {
        oAuth2ClientProperties.getRegistration().forEach((key, value) -> value.setRedirectUri(selfUrl + THIRD_PARTY_CODE_ENDPOINT + "/" + key));
    }
}