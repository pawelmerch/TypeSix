package io.mipt.typesix.web.controller;

import io.mipt.typesix.businesslogic.service.core.api.RegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static io.mipt.typesix.web.EndpointsList.*;

@RestController
@Log
@RequiredArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping(REGISTRATION_EMAIL_ENDPOINT)
    public ResponseEntity<?> acceptEmailEndpoint(@RequestParam("email") String email) {
        registrationService.beginRegistrationFlow(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping(REGISTRATION_CODE_ENDPOINT)
    public ResponseEntity<?> acceptCodeEndpoint(@RequestParam("code") String code, @RequestParam String email) {
        registrationService.checkValidCode(email, code);
        return ResponseEntity.ok().build();
    }

    @PostMapping(REGISTRATION_PASSWORD_SET_ENDPOINT)
    public ResponseEntity<?> passwordSetupEndpoint(@RequestParam String code, @RequestParam String password, @RequestParam String email, Model model) {
        registrationService.endRegistrationFlow(email, code, passwordEncoder.encode(password));
        return ResponseEntity.ok().build();
    }
}
