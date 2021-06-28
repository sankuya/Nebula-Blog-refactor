package com.stu.nebulablog.service.file.upload;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


public abstract class AbstractSharedFileUploadService extends AbstractFileUploadService {
    @Override
    protected boolean doUploadFile(String prePath, MultipartFile multipartFile) {
        String path=prePath+"/share/";
        File dir=new File(path);
        if(!dir.exists()){
            dir.mkdirs();
        }
        path+=multipartFile.getOriginalFilename();
        try {
            multipartFile.transferTo(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
