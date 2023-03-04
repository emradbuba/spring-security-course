package com.gitlab.emradbuba.learning.springsecuritycourse.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@Slf4j
public class TemplateController {

    @GetMapping("/login")
    public String getLoginView() {
        log.info("Requesting custom login page...");
        return "customLoginView";
    }

    @GetMapping("/afterLogin")
    public String afterLogin() {
        return "afterLogin";
    }
}
