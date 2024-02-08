package org.shlimtech.typesix.debug;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.shlimtech.typesixdatabasecommon.dto.UserDTO;
import org.shlimtech.typesixdatabasecommon.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;
import java.util.stream.Collectors;

@Configuration
@Profile("debug")
@RequiredArgsConstructor
@Log
public class DebugConfig {

    private static final SimpleUser[] debugUsers = {
            new SimpleUser("a"),
            new SimpleUser("b")
    };

    private final UserService userService;

    @PostConstruct
    public void initDb() {
        Arrays.stream(debugUsers)
                .map(debugUser -> UserDTO.builder()
                        .id(-1)
                        .email(debugUser.getEmail())
                        .firstName(debugUser.getName())
                        .biography("Test bio")
                        .login(debugUser.getName())
                        .lastName("TestLastName")
                        .build())
                .forEach(userService::createOrComplementUser);
    }

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer() {
        return (context) -> {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = context.get("org.springframework.security.core.Authentication.PRINCIPAL");
            User user = (User) usernamePasswordAuthenticationToken.getPrincipal();
            String email = Arrays.stream(debugUsers).filter(debugUser -> debugUser.getName().equals(user.getUsername())).findAny().orElseThrow().getEmail();
            UserDTO userDTO = userService.loadUser(email);
            context.getClaims().claim("email", userDTO.getEmail()).claim("id", userDTO.getId());
        };
    }

    @Bean
    public UserDetailsService users() {
        return new InMemoryUserDetailsManager(
                Arrays.stream(debugUsers)
                        .map(debugUser -> User.builder()
                                .username(debugUser.getName())
                                .password("{noop}" + debugUser.getPassword())
                                .roles("USER")
                                .build())
                        .collect(Collectors.toList()));
    }

    @Data
    private static class SimpleUser {
        private String name;
        private String email;
        private String password;

        public SimpleUser(String name) {
            this.email = this.password = this.name = name;
            this.email += "@gmail.com";
        }
    }

}
