package com.stu.nebulablog.old_controller;

import com.stu.nebulablog.mapper.UserInfoMapper;
import com.stu.nebulablog.module.entity.UserDetail;
import com.stu.nebulablog.module.entity.UserInfo;
import com.stu.nebulablog.service.article.ArticleListGetService;
import com.stu.nebulablog.service.info.InfoChangeService;
import com.stu.nebulablog.service.info.file.AbstractFileUploadService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

//@RestController
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
    private static final int size = 10;

    @PostMapping("/getArticleList")
    public Object getArticleList(@RequestBody JSONObject src, HttpSession session) {
        String type = src.getString("type");
        int page = src.getInt("page");
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
    public Object uploadPhoto(@RequestParam("file") MultipartFile multipartFile, HttpSession session) {
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
