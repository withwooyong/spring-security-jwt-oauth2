package com.example.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RestController
public class MessagesController {

    @GetMapping("/messages")
    public String[] getMessages(Principal principal) {
        log.debug("@GetMapping messages getMessages=" + principal.toString());
        return new String[]{"Message 1", "Message 2", "Message 3"};
    }
}
