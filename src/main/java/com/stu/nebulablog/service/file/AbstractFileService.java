package com.stu.nebulablog.service.file;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

public abstract class AbstractFileService {
    protected final String preUrl;

    public AbstractFileService(String preUrl) {
        this.preUrl = preUrl;
    }
}
