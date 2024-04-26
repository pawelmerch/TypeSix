package org.shlimtech.typesix.debug;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.shlimtech.typesixbusinesslogic.service.core.RegistrationException;
import org.shlimtech.typesixbusinesslogic.service.core.RegistrationService;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

@Configuration
@Profile("debug")
@RequiredArgsConstructor
@Log
public class DebugConfig {

    public static final String USER1 = "a";
    public static final String USER2 = "b";

    public static final String USER1_EMAIL = "a@gmail.com";
    public static final String USER2_EMAIL = "b@gmail.com";

    private final RegistrationService registrationService;

    private static final SimpleUser[] debugUsers = {
            new SimpleUser(USER1),
            new SimpleUser(USER2)
    };

    @PostConstruct
    public void createUsers() {
        AtomicReference<String> code = new AtomicReference<>("");
        registrationService.setCodeSender((userCode, email) -> {
            code.set(userCode);
            log.info("Received code: " + userCode);
        });

        Arrays.stream(debugUsers).forEach(user -> {
            try {
                registrationService.beginRegistrationFlow(user.getEmail());
                registrationService.endRegistrationFlow(user.getEmail(), code.get(), user.getPassword());
            } catch (RegistrationException ignored) {
            }
        });

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
