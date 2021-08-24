package com.stu.nebulablog.utils;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

@Component
public class PasswordUtil {
    private final String SALT = "*NebulA*";

    public String passwordEncoder(String password) {
        return DigestUtils.md5DigestAsHex((password + SALT).getBytes());
    }
}
