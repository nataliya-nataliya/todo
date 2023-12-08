package com.example.todo.security;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class JwtProperties {
    @Value("${security.jwt.secretKey}")
    private String secretKey;
}
