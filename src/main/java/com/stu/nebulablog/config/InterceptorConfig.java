package com.stu.nebulablog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home/index.html");
        registry.addViewController("/login").setViewName("login/index.html");
        registry.addViewController("/article").setViewName("article/index.html");
        registry.addViewController("/Q&A").setViewName("Q&A/index.html");
    }
}
