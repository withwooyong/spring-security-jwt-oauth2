package com.sp.sec.service;

import com.sp.sec.domain.AuthUser;
import com.sp.sec.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) {
        // it will be called at access token generation time
        Optional<AuthUser> optUser = userRepository.findByUserName(userName);
        if (optUser.isPresent()) {
            AuthUser user = optUser.get();
            List<GrantedAuthority> authorities = user.getRoles()
                    .stream().map(roles -> new SimpleGrantedAuthority(roles.getRoleName()))
                    .collect(Collectors.toList());
            return new User(user.getUserName(), user.getPassword(), authorities);
        }
        throw new RuntimeException("user not exist");
    }
}
