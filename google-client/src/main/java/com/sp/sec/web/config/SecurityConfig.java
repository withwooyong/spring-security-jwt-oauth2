package com.sp.sec.web.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;

@Slf4j
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final SpGoogleUser spGoogleUser;
    private final SpGoogleUserToMyUserFilter googleUserToMyUserFilter;
    CommonOAuth2Provider provider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().and()
                .oauth2Login(oauth -> {
                    oauth.userInfoEndpoint(userinfo -> {
                        userinfo.oidcUserService(spGoogleUser);
                    });
                })
                .addFilterAfter(googleUserToMyUserFilter, OAuth2LoginAuthenticationFilter.class)
        ;
    }
}
