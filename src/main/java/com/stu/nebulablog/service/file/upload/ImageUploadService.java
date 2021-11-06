package com.stu.nebulablog.service.file.upload;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class ImageUploadService extends AbstractFileUploadService {
    public ImageUploadService(String preUrl) {
        super(preUrl);
    }

    @Override
    protected boolean doUploadFile(String prePath, MultipartFile multipartFile) {
        String path = prePath + "/img/";
        File directory = new File(path);
        path += multipartFile.getOriginalFilename();
        if (!directory.exists()) directory.mkdirs();
        try {
            multipartFile.transferTo(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean uploadImage(String prePath, MultipartFile multipartFile) {
        return super.uploadFile(prePath, multipartFile);
    }
}
