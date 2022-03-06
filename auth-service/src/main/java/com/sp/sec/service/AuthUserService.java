package com.sp.sec.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sp.sec.domain.AuthUser;
import com.sp.sec.repository.UserRepository;
import com.sp.sec.repository.UserRoleRepository;
import com.sp.sec.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthUserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthUser register(UserDto userDto) {
        System.out.println("==============2");
        log.debug("userDto={}", userDto.toString());
        AuthUser authUser = new ObjectMapper().convertValue(userDto, AuthUser.class);
        log.debug("authUser={}", authUser.toString());
        authUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        log.debug("0");
        authUser.setRoles(Collections.singletonList(userRoleRepository.findByRoleNameContaining("USER")));
        log.debug("1");
        Optional<AuthUser> optUser = userRepository.findByUserNameOrEmail(userDto.getUserName(), userDto.getEmail());
        log.debug("2");
        if (!optUser.isPresent()) {
            log.debug("3");
            System.out.println("==============3");
            return userRepository.save(authUser);
        }
        throw new RuntimeException("User already exist");
    }
}
