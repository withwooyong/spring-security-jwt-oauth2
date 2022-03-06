package com.sp.sec.web.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SecuredService {

    @PreAuthorize("hasAnyAuthority('FROM_GOOGLE')")
    public String secured() {
        log.debug("secured");
        return "secured info : 옥수수";
    }

}
