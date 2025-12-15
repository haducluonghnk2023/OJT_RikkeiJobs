package com.data.db_rikkeijobs.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class AppConfig {
    
    @Value("${cors.allowed-origins}")
    private String corsAllowedOrigins;
}

