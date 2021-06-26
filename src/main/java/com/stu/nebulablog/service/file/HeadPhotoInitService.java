package com.stu.nebulablog.service.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HeadPhotoInitService {
    @Value("${user.photo.preUrl}")
    private String prePath;
    public void InitHeadPhoto(String  username){

    }
}
