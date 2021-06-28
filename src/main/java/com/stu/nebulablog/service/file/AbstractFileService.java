package com.stu.nebulablog.service.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

public abstract class AbstractFileService {
    @Value("${user.photo.preUrl}")
    protected String preUrl;
}
