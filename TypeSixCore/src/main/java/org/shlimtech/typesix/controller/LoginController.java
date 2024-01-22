package org.shlimtech.typesix.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/login")
public class LoginController  {

    @GetMapping
    public RedirectView index() {
        return new RedirectView("/oauth2/authorization/yandex");
    }

}
