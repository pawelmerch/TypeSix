package org.shlimtech.typesix.web.controller;

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

import static org.shlimtech.typesix.web.EndpointsList.*;

@Controller
@Log
@RequiredArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;

    //==========================================

    @GetMapping(REGISTRATION_EMAIL_PAGE)
    public String emailPage(Model model) {
        model.addAttribute("email_endpoint", REGISTRATION_EMAIL_ENDPOINT);
        return "email";
    }

    @PostMapping(REGISTRATION_EMAIL_ENDPOINT)
    public String acceptEmailEndpoint(@RequestParam("email") String email) {
        registrationService.beginRegistrationFlow(email);
        return "redirect:" + REGISTRATION_CODE_PAGE + "?email=" + email;
    }

    //==========================================

    @GetMapping(REGISTRATION_CODE_PAGE)
    public String codePage(Model model, @RequestParam String email) {
        model.addAttribute("email", email);
        model.addAttribute("code_endpoint", REGISTRATION_CODE_ENDPOINT);
        return "code";
    }

    @PostMapping(REGISTRATION_CODE_ENDPOINT)
    public String acceptCodeEndpoint(@RequestParam("code") String code, @RequestParam String email) {
        registrationService.checkValidCode(email, code);
        return "redirect:" + REGISTRATION_PASSWORD_SET_PAGE + "?code=" + code + "&email=" + email;
    }

    //==========================================

    @GetMapping(REGISTRATION_PASSWORD_SET_PAGE)
    public String passwordChangePage(@RequestParam String code, @RequestParam String email, Model model) {
        model.addAttribute("code", code);
        model.addAttribute("email", email);
        model.addAttribute("password_endpoint", REGISTRATION_PASSWORD_SET_ENDPOINT);
        return "password";
    }

    @PostMapping(REGISTRATION_PASSWORD_SET_ENDPOINT)
    public String passwordSetupEndpoint(@RequestParam String code, @RequestParam String password, @RequestParam String email, Model model) {
        registrationService.endRegistrationFlow(email, code, password);
        return "redirect:" + LOGIN_PAGE;
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
