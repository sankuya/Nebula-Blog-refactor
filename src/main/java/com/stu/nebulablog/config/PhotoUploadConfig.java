package com.stu.nebulablog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class PhotoUploadConfig implements WebMvcConfigurer {
    @Value("${user.photo.preUrl}")
    private String preUrl;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String path = preUrl;
        registry.addResourceHandler("/user/**").addResourceLocations("file:" + path);
    }
}
