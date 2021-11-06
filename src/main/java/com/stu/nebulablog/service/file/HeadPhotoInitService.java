package com.stu.nebulablog.service.file;

import com.stu.nebulablog.module.entity.UserInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class HeadPhotoInitService extends AbstractFileService {
    public HeadPhotoInitService(String preUrl) {
        super(preUrl);
    }

    @Async
    public void initHeadPhoto(String username) {
        Path source = Paths.get(preUrl + "ProfilePhoto.jpg");
        File dir = new File(preUrl + username);
        File file = new File(preUrl + username + "/ProfilePhoto.jpg");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            if (file.exists()) {
                file.delete();
            }
            Files.copy(source, file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
