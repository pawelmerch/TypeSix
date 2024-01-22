package org.shlimtech.typesix.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController  {

    @GetMapping
    public String index() {
        return "redirect://oauth2/authorization/yandex";
    }

}
