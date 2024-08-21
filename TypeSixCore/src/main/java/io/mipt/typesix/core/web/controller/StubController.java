package io.mipt.typesix.core.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import static io.mipt.typesix.core.web.EndpointsList.PREFIX_PUBLIC_PAGE;

@Controller
public class StubController {
    private final ModelAndView RESULT = new ModelAndView("index");

    @GetMapping(PREFIX_PUBLIC_PAGE + "/{resource}")
    public ModelAndView successLoginPage(@PathVariable String resource) {
        return RESULT;
    }
}
