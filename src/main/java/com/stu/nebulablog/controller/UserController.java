package com.stu.nebulablog.controller;

import com.stu.nebulablog.mapper.UserInfoMapper;
import com.stu.nebulablog.module.ResponseData;
import com.stu.nebulablog.module.entity.UserDetail;
import com.stu.nebulablog.module.entity.UserInfo;
import com.stu.nebulablog.service.file.upload.ImageUploadService;
import com.stu.nebulablog.service.user.UserInfoChangeService;
import com.stu.nebulablog.service.file.upload.PhotoUploadService;
import com.stu.nebulablog.service.user.UserGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserGetService userGetService;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private ImageUploadService imageUploadService;
    @Autowired
    private UserInfoChangeService userInfoChangeService;
    @Autowired
    private PhotoUploadService photoUploadService;

    @PostMapping("getUser")
    public ResponseData getUserData(HttpSession session) {
        Integer uid = (Integer) session.getAttribute("uid");
        ResponseData responseData = new ResponseData();
        responseData.setCode(300);
        responseData.setData(userGetService.doGetUser(uid));
        return responseData;
    }

    @PostMapping("infoChange")
    public ResponseData infoChange(@RequestBody UserDetail src, HttpSession session) {
        ResponseData responseData = new ResponseData();
        Integer uid = (Integer) session.getAttribute("uid");
        src.setUid(uid);
        if (userInfoChangeService.doInfoChange(src)) {
            responseData.setCode(300);
        } else {
            responseData.setCode(301);
        }
        return responseData;
    }

    @PostMapping("/uploadPhoto")
    public Object uploadPhoto(HttpServletRequest httpServletRequest, HttpSession session) {
        ResponseData responseData = new ResponseData();
        MultipartFile multipartFile;
        try {
            multipartFile = ((MultipartHttpServletRequest) httpServletRequest).getFiles("file").get(0);
        } catch (ClassCastException | IndexOutOfBoundsException e) {
            responseData.setCode(302);
            responseData.setMsg("文件不能为空");
            return responseData;
        }
        Integer uid = (Integer) session.getAttribute("uid");
        UserInfo userInfo = userInfoMapper.selectById(uid);
        if (userInfo != null && photoUploadService.uploadPhoto(userInfo.getUsername(), multipartFile)) {
            responseData.setCode(300);
            responseData.setData("/user/" + userInfo.getUsername() + "/ProfilePhoto.jpg");
        } else {
            responseData.setCode(301);
        }
        return responseData;
    }

    @PostMapping("uploadImage")
    public ResponseData uploadImage(HttpServletRequest httpServletRequest, HttpSession session) {
        ResponseData responseData = new ResponseData();
        MultipartFile multipartFile;
        try {
            multipartFile = ((MultipartHttpServletRequest) httpServletRequest).getFiles("editormd-image-file").get(0);
        } catch (ClassCastException | IndexOutOfBoundsException e) {
            responseData.setCode(302);
            responseData.setMsg("文件不能为空");
            return responseData;
        }
        try {
            Integer uid = (Integer) session.getAttribute("uid");
            UserInfo userInfo = userInfoMapper.selectById(uid);
            if (userInfo != null && imageUploadService.uploadImage(userInfo.getUsername(), multipartFile)) {
                responseData.setCode(300);
                responseData.setData("/user/" + userInfo.getUsername() + "/img/" + multipartFile.getOriginalFilename());
            } else {
                responseData.setCode(301);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseData;
    }
}
