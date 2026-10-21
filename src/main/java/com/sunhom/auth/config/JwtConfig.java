package com.sunhom.auth.config;

import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

@Configuration
@Getter
public class JwtConfig {

    @Value("${security.jwt.secret}")
    private String secret;

    @Value("${security.jwt.access-token-expiry}")
    private long accessTokenExpiry; // seconds

    @Value("${security.jwt.issuer}")
    private String issuer;
}
