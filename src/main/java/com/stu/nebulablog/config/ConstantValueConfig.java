package com.stu.nebulablog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConstantValueConfig {
    private final String preUrl;
    private final int maxSize;

    public ConstantValueConfig(@Value("${user.photo.preUrl}") String preUrl,
                               @Value("${page-max-size}") int maxSize) {
        this.preUrl = preUrl;
        this.maxSize = maxSize;
    }

    @Bean
    public String preUrl() {
        return this.preUrl;
    }

    @Bean
    public int maxSize() {
        return this.maxSize;
    }
}
