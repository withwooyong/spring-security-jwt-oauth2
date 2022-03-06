package com.sp.sec.web.config;


import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class SpJwtProperties {

    private String secret = "default-secret-value";
    private long tokenLifeTime = 600;
    private long tokenRefreshTime = 24 * 60 * 60; // 86400

}
