package org.shlimtech.typesix.controller;

import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.shlimtech.typesix.security.EndpointsList.*;

@Controller
@Log
public class RegistrationController {

    //==========================================

    @GetMapping(EMAIL_PAGE)
    public String emailPage(Model model) {
        model.addAttribute("code_page", CODE_PAGE);
        model.addAttribute("email_page", EMAIL_PAGE);
        return "email";
    }

    @PostMapping(EMAIL_PAGE)
    public String acceptEmailEndpoint(@RequestParam("email") String email) {
        beginRegistrationFlowStub(email);
        return "redirect:" + CODE_PAGE + "?email=" + email;
    }

    //==========================================

    @GetMapping(CODE_PAGE)
    public String codePage(Model model, @RequestParam String email) {
        model.addAttribute("email", email);
        model.addAttribute("code_page", CODE_PAGE);
        model.addAttribute("password_page", PASSWORD_CHANGE_PAGE);
        return "code";
    }

    @PostMapping(CODE_PAGE)
    public String acceptCodeEndpoint(@RequestParam("code") String code, @RequestParam String email) {
        checkValidCodeStub(email, code);
        return "redirect:" + PASSWORD_CHANGE_PAGE + "?code=" + code + "&email=" + email;
    }

    //==========================================

    @GetMapping(PASSWORD_CHANGE_PAGE)
    public String passwordChangePage(@RequestParam String code, @RequestParam String email, Model model) {
        model.addAttribute("code", code);
        model.addAttribute("email", email);
        model.addAttribute("password_endpoint", PASSWORD_SET_ENDPOINT);
        return "password";
    }

    @PostMapping(PASSWORD_SET_ENDPOINT)
    public String passwordSetupEndpoint(@RequestParam String code, @RequestParam String password, @RequestParam String email, Model model) {
        endRegistrationFlowStub(email, code, password);
        return "redirect:" + LOGIN_ENDPOINT;
    }

    //==========================================

    private void beginRegistrationFlowStub(String email) {
        log.info("beginRegistrationFlowStub: email=" + email);
    }

    private void checkValidCodeStub(String email, String code) {
        log.info("checkValidCodeStub: email=" + email + ", code=" + code);
    }

    private void endRegistrationFlowStub(String email, String code, String password) {
        log.info("endRegistrationFlowStub: email=" + email + ", code=" + code + ", password=" + password);
    }
}
