package com.stu.nebulablog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("profile.properties")
public class NebulaBlogRefactorApplication {

    public static void main(String[] args) {
        SpringApplication.run(NebulaBlogRefactorApplication.class, args);
    }

}
