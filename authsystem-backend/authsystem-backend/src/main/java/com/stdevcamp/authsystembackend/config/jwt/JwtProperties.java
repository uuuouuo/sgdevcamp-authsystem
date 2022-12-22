package com.stdevcamp.authsystembackend.config.jwt;

/**
 * JWT의 정보
 */
public interface JwtProperties {
    String SECRET = "JSM";
    int EXPIRATION_TIME = 1000 * 60 * 10;
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "JWT";
}
