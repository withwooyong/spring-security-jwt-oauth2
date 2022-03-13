package com.sp.sec.web.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SpOAuth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.debug("userRequest={}", userRequest.toString());
        OAuth2User user = super.loadUser(userRequest);
        // TODO : ExtendedUser 로 바꾼뒤 리턴한다.
        log.debug("loadUser OAuth2User={}", user.toString());
        return user;
    }
}
