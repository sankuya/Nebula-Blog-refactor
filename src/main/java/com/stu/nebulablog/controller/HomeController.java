package com.stu.nebulablog.controller;

import com.stu.nebulablog.mapper.UserInfoMapper;
import com.stu.nebulablog.module.UserDetail;
import com.stu.nebulablog.module.UserInfo;
import com.stu.nebulablog.service.article.ArticleListGetService;
import com.stu.nebulablog.service.info.InfoChangeService;
import com.stu.nebulablog.service.info.file.AbstractFileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private ArticleListGetService articleListGetService;
    @Autowired
    private InfoChangeService infoChangeService;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private AbstractFileUploadService photoUploadService;
    private final int size = 10;

    @PostMapping("/getArticleList")
    public Object getArticleList(@RequestBody Map<String,String > src, HttpSession session) {
        String type = src.get("type");
        Integer page = Integer.valueOf(src.get("page"));
        if (type == null) return null;
        Map<String, Object> res;
        if (type.equals("all")) {
            res = articleListGetService.getAllArticleList(page, size);
        } else {
            Integer uid = (Integer) session.getAttribute("userid");
            if(uid==null)return null;
            res = articleListGetService.getArticleListByUserId(page, uid, size);
        }
        return res;
    }

    @PostMapping("/infoChange")
    public Object infoChange(@RequestBody UserDetail src, HttpSession session) {
        Integer uid = (Integer) session.getAttribute("userid");
        if (uid == null) return "wrong";
        src.setUid(uid);
        if (infoChangeService.doInfoChange(src)) return "ok";
        else return "wrong";
    }

    @PostMapping("/uploadPhoto")
    public Object uploadPhoto(HttpServletRequest httpServletRequest, HttpSession session) {
        MultipartFile multipartFile=((MultipartHttpServletRequest)httpServletRequest).getFiles("file").get(0);
        Map<String, String> res = new HashMap<>();
        Integer uid = (Integer) session.getAttribute("userid");
        UserInfo userInfo = userInfoMapper.selectById(uid);
        res.put("status", "wrong");
        if (userInfo == null) return res;
        if (photoUploadService.uploadPhoto(userInfo, multipartFile)) {
            res.put("status", "ok");
            res.put("path", "../" + userInfo.getUsername() + "/ProfilePhoto.jpg");
        }
        return res;
    }
}
