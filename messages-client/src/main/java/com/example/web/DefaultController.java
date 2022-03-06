package com.example.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Slf4j
@Controller
public class DefaultController {

    @GetMapping("/")
    public String root(Principal principal) {
        log.debug("root=" + principal.toString());
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String index(Principal principal) {
        log.debug("index=" + principal.toString());
        return "index";
    }
}
