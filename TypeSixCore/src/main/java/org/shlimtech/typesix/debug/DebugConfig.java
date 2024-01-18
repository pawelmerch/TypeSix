package org.shlimtech.typesix.debug;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.shlimtech.typesixdatabasecommon.dto.UserDTO;
import org.shlimtech.typesixdatabasecommon.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@Profile("debug")
@RequiredArgsConstructor
@Log
public class DebugConfig {

    private final UserService userService;

    @PostConstruct
    public void initDb() {
        userService.createOrComplementUser(new UserDTO(-1, "test@gmail.com", "admin", "", "", "", "", "", "", "", null));
    }

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer() {
        return (context) -> {
            String email = "test@gmail.com";
            UserDTO userDTO = userService.loadUser(email);
            context.getClaims().claim("email", userDTO.getEmail()).claim("id", userDTO.getId());
        };
    }

    @Bean
    public UserDetailsService users() {
        UserDetails user = User.builder()
                .username("a")
                .password("{noop}a")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

}
