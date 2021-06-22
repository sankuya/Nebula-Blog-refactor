package com.stu.nebulablog.service.file;

import com.stu.nebulablog.module.entity.UserInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
abstract public class AbstractFileUploadService {
    @Value("${user.photo.preUrl}")
    private String preUrl;

    @Async
    abstract protected void doUploadPhoto(String prePath, MultipartFile multipartFile);

    public boolean uploadPhoto(UserInfo userInfo, MultipartFile multipartFile) {
        String username = userInfo.getUsername();
        String tmpPath = preUrl + username;
        File directory = new File(tmpPath);
        try {
            if (!directory.exists()) directory.mkdirs();
            doUploadPhoto(tmpPath, multipartFile);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}