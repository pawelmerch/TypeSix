package org.shlimtech.typesix.web.debug;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.shlimtech.typesixbusinesslogic.domain.model.User;
import org.shlimtech.typesixbusinesslogic.domain.model.UserStatus;
import org.shlimtech.typesixbusinesslogic.service.impl.repository.UserRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("debug")
@RequiredArgsConstructor
@Log
public class DebugUsersConfig {
    public static final String USER1 = "a";
    public static final String USER2 = "b";

    public static final String USER1_EMAIL = "a@gmail.com";
    public static final String USER2_EMAIL = "b@gmail.com";

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
