package com.stu.nebulablog.service.info.file;

import com.stu.nebulablog.module.UserInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
abstract public class AbstractFileUploadService {
    @Value("${user.photo.preUrl}")
    private String preUrl;

    abstract protected boolean doUploadPhoto(String prePath, MultipartFile multipartFile);

    public boolean uploadPhoto(UserInfo userInfo, MultipartFile multipartFile) {
        String username = userInfo.getUsername();
        String tmpPath = preUrl+  username ;
        File directory = new File(tmpPath);
        if (!directory.exists()) directory.mkdirs();
        return doUploadPhoto(tmpPath, multipartFile);
    }
}
