package com.sp.sec.web;

import com.sp.sec.domain.AuthUser;
import com.sp.sec.service.AuthUserService;
import com.sp.sec.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/oauth/users")
public class UserController {

    private final AuthUserService authUserService;

    @GetMapping("/test")
    public String home() {
        log.debug("home");
        return "home";
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuthUser register(@RequestBody UserDto userDto) {
        System.out.println("==============1");
        log.debug(userDto.toString());
        return authUserService.register(userDto);
    }

}
