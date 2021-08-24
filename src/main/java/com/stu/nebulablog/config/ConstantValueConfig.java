package com.stu.nebulablog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConstantValueConfig {
    private final String preUrl;

    public ConstantValueConfig(@Value("${user.photo.preUrl}") String preUrl) {
        this.preUrl = preUrl;
    }

    @Bean
    public String preUrl() {
        return this.preUrl;
    }
}
