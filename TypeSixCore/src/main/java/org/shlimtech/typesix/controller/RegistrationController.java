package org.shlimtech.typesix.controller;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.shlimtech.typesixbusinesslogic.service.core.RegistrationException;
import org.shlimtech.typesixbusinesslogic.service.core.RegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.shlimtech.typesix.security.EndpointsList.*;

@Controller
@Log
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostConstruct
    public void setRabbitMQStub() {
        registrationService.setCodeSender((code, email) -> {
            log.info("CODE: " + code); // TODO replace this with rabbitMQ adapter call
        });
    }

    //==========================================

    @GetMapping(EMAIL_ENDPOINT)
    public String emailPage(Model model) {
        model.addAttribute("email_page", EMAIL_ENDPOINT);
        return "email";
    }

    @PostMapping(EMAIL_ENDPOINT)
    public String acceptEmailEndpoint(@RequestParam("email") String email) {
        registrationService.beginRegistrationFlow(email);
        return "redirect:" + CODE_ENDPOINT + "?email=" + email;
    }

    //==========================================

    @GetMapping(CODE_ENDPOINT)
    public String codePage(Model model, @RequestParam String email) {
        model.addAttribute("email", email);
        model.addAttribute("code_page", CODE_ENDPOINT);
        return "code";
    }

    @PostMapping(CODE_ENDPOINT)
    public String acceptCodeEndpoint(@RequestParam("code") String code, @RequestParam String email) {
        registrationService.checkValidCode(email, code);
        return "redirect:" + PASSWORD_SET_ENDPOINT + "?code=" + code + "&email=" + email;
    }

    //==========================================

    @GetMapping(PASSWORD_SET_ENDPOINT)
    public String passwordChangePage(@RequestParam String code, @RequestParam String email, Model model) {
        model.addAttribute("code", code);
        model.addAttribute("email", email);
        model.addAttribute("password_endpoint", PASSWORD_SET_ENDPOINT);
        return "password";
    }

    @PostMapping(PASSWORD_SET_ENDPOINT)
    public String passwordSetupEndpoint(@RequestParam String code, @RequestParam String password, @RequestParam String email, Model model) {
        registrationService.endRegistrationFlow(email, code, password);
        return "redirect:" + LOGIN_ENDPOINT;
    }

    //==========================================

    @ExceptionHandler
    public String registrationExceptionHandler(RegistrationException registrationException) {
        return "redirect:" + ERROR_PAGE + "?message=" + registrationException.getMessage();
    }

    @ExceptionHandler
    public String unknownExceptionHandler(Exception exception) {
        return "redirect:" + ERROR_PAGE + "?message=Unknown exception: " + exception.getMessage();
    }
}
