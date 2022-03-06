package com.sp.sec.web.config;

import com.sp.sec.user.domain.Authority;
import com.sp.sec.user.domain.User;
import com.sp.sec.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Component
public class SpGoogleUserToMyUserFilter implements Filter {

    private final UserService userService;

    //    https://accounts.google.com/o/oauth2/v2/auth/oauthchooseaccount?
//    response_type=code&client_id=377825014181-2tpha7niq0hjar86e4j8h6dl0h8j1s3i.apps.googleusercontent.com&
//    scope=openid%20profile%20email&state=QRGC-laPIh3ZNzKTrRCm66NyMjiMp_QXkSTQWTGUY3g%3D&redirect_uri=http%3A%2F%2Flocalhost%3A9006%2F
//    login%2Foauth2%2Fcode%2Fgoogle&nonce=Llhl64o-b5072sOgjpq-utt9suA5fRrebg5OLbz1AmU&flowName=GeneralOAuthFlow
//    http://localhost:8080/login/oauth2/code/google
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.debug("doFilter");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof OAuth2AuthenticationToken) {
            OidcUser googleUser = (OidcUser) ((OAuth2AuthenticationToken) auth).getPrincipal();
            log.debug("googleUser={}", googleUser.toString());
            User user = userService.findUser("google_" + googleUser.getSubject())
                    .orElseGet(() ->
                            userService.save(User.builder()
                                    .userId("google_" + googleUser.getSubject())
                                    .email(googleUser.getEmail())
                                    .authorities(Set.of(Authority.USER, new Authority("FROM_GOOGLE")))
                                    .enabled(true)
                                    .build())
                    );
            log.debug("user={}", user.toString());
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities())
            );
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

}
