package com.stu.nebulablog.controller;

import com.stu.nebulablog.mapper.UserInfoMapper;
import com.stu.nebulablog.module.ResponseData;
import com.stu.nebulablog.module.entity.UserDetail;
import com.stu.nebulablog.module.entity.UserInfo;
import com.stu.nebulablog.service.file.upload.ImageUploadService;
import com.stu.nebulablog.service.user.UserInfoChangeService;
import com.stu.nebulablog.service.file.upload.PhotoUploadService;
import com.stu.nebulablog.service.user.UserGetService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("user")
@AllArgsConstructor
public class UserController {
    private final UserGetService userGetService;
    private final UserInfoMapper userInfoMapper;
    private final ImageUploadService imageUploadService;
    private final UserInfoChangeService userInfoChangeService;
    private final PhotoUploadService photoUploadService;

    @GetMapping("getUser")
    public ResponseData getUserData(HttpSession session) {
        Integer uid = (Integer) session.getAttribute("uid");
        ResponseData responseData = ResponseData.success();
        responseData.setData(userGetService.doGetUser(uid));
        return responseData;
    }

    @PostMapping("infoChange")
    public ResponseData infoChange(@RequestBody UserDetail src, HttpSession session) {
        Integer uid = (Integer) session.getAttribute("uid");
        src.setUid(uid);
        if (userInfoChangeService.doInfoChange(src)) {
            return ResponseData.success();
        } else {
            return ResponseData.fail();
        }
    }

    @PostMapping("uploadPhoto")
    public Object uploadPhoto(HttpServletRequest httpServletRequest, HttpSession session) {
        MultipartFile multipartFile;
        try {
            multipartFile = ((MultipartHttpServletRequest) httpServletRequest).getFiles("file").get(0);
        } catch (ClassCastException | IndexOutOfBoundsException e) {
            return ResponseData.fail();
        }
        Integer uid = (Integer) session.getAttribute("uid");
        UserInfo userInfo = userInfoMapper.selectById(uid);
        if (userInfo != null && photoUploadService.uploadPhoto(userInfo.getUsername(), multipartFile)) {
            ResponseData responseData = ResponseData.success();
            responseData.setData("/user/" + userInfo.getUsername() + "/ProfilePhoto.jpg");
            return responseData;
        } else {
            return ResponseData.fail();
        }
    }

    @PostMapping("uploadImage")
    public ResponseData uploadImage(HttpServletRequest httpServletRequest, HttpSession session) {
        MultipartFile multipartFile;
        try {
            multipartFile = ((MultipartHttpServletRequest) httpServletRequest).getFiles("editormd-image-file").get(0);
        } catch (ClassCastException | IndexOutOfBoundsException e) {
            return ResponseData.fail();
        }
        Integer uid = (Integer) session.getAttribute("uid");
        if (uid == null) return ResponseData.fail();
        UserInfo userInfo = userInfoMapper.selectById(uid);
        if (userInfo != null && imageUploadService.uploadImage(userInfo.getUsername(), multipartFile)) {
            ResponseData responseData = ResponseData.success();
            responseData.setData("/user/" + userInfo.getUsername() + "/img/" + multipartFile.getOriginalFilename());
            return responseData;
        } else {
            return ResponseData.fail();
        }
    }
}
