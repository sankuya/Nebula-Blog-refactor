package com.stu.nebulablog.service.file;

import com.stu.nebulablog.service.file.AbstractFileUploadService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class ImageUploadService extends AbstractFileUploadService {
    @Override
    protected boolean doUploadPhoto(String prePath, MultipartFile multipartFile) {
        String path=prePath+"/img/"+multipartFile.getOriginalFilename();
        try {
            multipartFile.transferTo(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
