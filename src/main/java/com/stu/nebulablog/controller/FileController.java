package com.stu.nebulablog.controller;

import com.stu.nebulablog.module.ResponseData;
import com.stu.nebulablog.service.file.upload.SharedFileDeleteService;
import com.stu.nebulablog.service.file.upload.SharedFileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("file")
public class FileController {
    @Autowired
    private SharedFileUploadService sharedFileUploadService;
    @Autowired
    private SharedFileDeleteService sharedFileDeleteService;
    @PostMapping("/uploadSharedFile")
    public Object uploadPhoto(HttpServletRequest httpServletRequest, HttpSession session) {
        ResponseData responseData = new ResponseData();
        MultipartFile multipartFile;
        try {
            multipartFile = ((MultipartHttpServletRequest) httpServletRequest).getFiles("file").get(0);
        } catch (ClassCastException | IndexOutOfBoundsException e) {
            responseData.setCode(901);
            responseData.setMsg("文件不能为空");
            return responseData;
        }
        Integer uid = (Integer) session.getAttribute("uid");
        sharedFileUploadService.uploadSharedFile(uid,multipartFile);
        responseData.setCode(900);
        return responseData;
    }

}
