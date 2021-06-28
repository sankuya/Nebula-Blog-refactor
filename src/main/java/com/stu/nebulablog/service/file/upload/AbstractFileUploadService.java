package com.stu.nebulablog.service.file.upload;

import com.stu.nebulablog.module.entity.UserInfo;
import com.stu.nebulablog.service.file.AbstractFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;


abstract public class AbstractFileUploadService extends AbstractFileService {

    @Async
    abstract protected boolean doUploadFile(String prePath, MultipartFile multipartFile);

    protected boolean uploadFile(String  username, MultipartFile multipartFile) {
        String tmpPath = preUrl + username;
        File directory = new File(tmpPath);
        try {
            if (!directory.exists()) directory.mkdirs();
            doUploadFile(tmpPath, multipartFile);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
