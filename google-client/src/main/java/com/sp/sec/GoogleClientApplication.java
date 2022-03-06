package com.sp.sec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GoogleClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoogleClientApplication.class, args);
    }

//    https://accounts.google.com/o/oauth2/v2/auth/oauthchooseaccount?response_type=code&
//    client_id=377825014181-2tpha7niq0hjar86e4j8h6dl0h8j1s3i.apps.googleusercontent.com&scope=openid%20
//    profile%20email&state=a8ZHYf5ftwotjPEmO32IXnksIu3UqaKSaPdlAmiC8QQ%3D&redirect_uri=http%3A%2F%2Flocalhost%3A9006%2Flogin%2Foauth2%2Fcode%2Fgoogle&
//    nonce=FZAInGQBN9kmGW9hE6N-uvQq49BI3OQytS4Myat5bKs&flowName=GeneralOAuthFlow

}
