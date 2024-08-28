package io.mipt.typesix.core.web.debug;

import io.mipt.typesix.businesslogic.domain.model.User;
import io.mipt.typesix.businesslogic.domain.model.UserStatus;
import io.mipt.typesix.businesslogic.service.core.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@Profile("debug || test")
@RequiredArgsConstructor
@Log
public class DebugUsersConfig {
    public static final String USER1 = "a";
    public static final String USER2 = "b";

    public static final String USER1_EMAIL = "a@gmail.com";
    public static final String USER2_EMAIL = "b@gmail.com";

    private static final String DEFAULT_ROLE = "admin";

    private final UserRepository userRepository;

    private static final SimpleUser[] debugUsers = {
            new SimpleUser(USER1),
            new SimpleUser(USER2)
    };

    @PostConstruct
    @Transactional
    public void createUsers() {
        if (userRepository.findByEmail(USER1_EMAIL) == null) {
            userRepository.save(User.builder()
                    .firstName(USER1)
                    .lastName(USER1)
                    .login(USER1)
                    .email(USER1_EMAIL)
                    .role(DEFAULT_ROLE)
                    .password(USER1)
                    .status(UserStatus.active)
                    .build()
            );
        }
        if (userRepository.findByEmail(USER2_EMAIL) == null) {
            userRepository.save(User.builder()
                    .firstName(USER2)
                    .lastName(USER2)
                    .login(USER2)
                    .email(USER2_EMAIL)
                    .role(DEFAULT_ROLE)
                    .password(USER2)
                    .status(UserStatus.active)
                    .build()
            );
        }
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
