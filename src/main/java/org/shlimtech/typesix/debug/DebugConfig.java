package org.shlimtech.typesix.debug;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.shlimtech.typesix.dto.UserDTO;
import org.shlimtech.typesix.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.client.RestTemplate;

@Configuration
@Profile("debug")
@RequiredArgsConstructor
@EnableScheduling
@Log
public class DebugConfig {

    private final UserService userService;

    @PostConstruct
    public void initDb() {
        userService.createOrComplementUser(new UserDTO(-1, "test@gmail.com", "admin", "", "", "", "", "", "", ""));
    }

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer() {
        return (context) -> {
            String email = "test@gmail.com";
            UserDTO userDTO = userService.loadUser(email);
            context.getClaims().claim("email", userDTO.getEmail()).claim("id", userDTO.getId());
        };
    }

    @Scheduled(fixedRate = 1000)
    public void testType6d() {
        RestTemplate restTemplate = new RestTemplate();
        log.info(restTemplate.getForEntity("http://10.96.169.230:443/task", String.class).toString());
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
