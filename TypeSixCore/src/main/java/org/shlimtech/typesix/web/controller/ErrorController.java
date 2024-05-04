package org.shlimtech.typesix.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.shlimtech.typesix.web.EndpointsList.ERROR_PAGE;
import static org.shlimtech.typesix.web.EndpointsList.LOGIN_PAGE;

@Controller
public class ErrorController {
    @GetMapping(ERROR_PAGE)
    public String errorPage(@RequestParam(required = false) String message, Model model) {
        model.addAttribute("message", message != null ? message : "No error message");
        model.addAttribute("login_page", LOGIN_PAGE);
        return "error";
    }
}
