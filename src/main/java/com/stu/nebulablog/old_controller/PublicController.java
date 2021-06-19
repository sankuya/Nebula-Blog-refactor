package com.stu.nebulablog.old_controller;

import com.stu.nebulablog.mapper.UserInfoMapper;
import com.stu.nebulablog.module.entity.UserInfo;
import com.stu.nebulablog.service.info.ImageUploadService;
import com.stu.nebulablog.service.info.UserDataGetService;
import com.stu.nebulablog.utils.PasswordUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/public")
//@RestController
public class PublicController {
    @Autowired
    private UserDataGetService userDataGetService;
    @Autowired
    private PasswordUtil passwordUtil;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private ImageUploadService imageUploadService;

    @PostMapping("getUserData")
    public Map<String, Object> getUserData(HttpSession session, @Nullable @CookieValue(value = "uid") String username, @Nullable @CookieValue(value = "password") String password) {
        Integer uid = (Integer) session.getAttribute("userid");
        if (uid == null) {
            if (username != null && password != null && passwordUtil.passwordEncoder(username).equals(password))
                uid = Integer.valueOf(username);
            else {
                Map<String, Object> res = new HashMap<>();
                res.put("nickname", "登录/注册");
                return res;
            }
        }
        return userDataGetService.doGetUserData(uid);
    }

    @PostMapping("getUserDataByID")
    public Map<String, Object> getUserDataByID(@RequestBody JSONObject src) {
        Integer uid = Integer.valueOf(src.getString("uid"));
        if (uid == null) return null;
        return userDataGetService.doGetUserData(uid);
    }

    @PostMapping("logout")
    public String logout(HttpSession session, HttpServletResponse httpServletResponse) {
        if (session.getAttribute("userid") != null) session.removeAttribute("userid");
        Cookie usernameCookie = new Cookie("username", null);
        Cookie passwordCookie = new Cookie("password", null);

        usernameCookie.setMaxAge(0);
        passwordCookie.setMaxAge(0);
        httpServletResponse.addCookie(usernameCookie);
        httpServletResponse.addCookie(passwordCookie);
        return "ok";
    }

    @PostMapping("uploadImage")
    public Object uploadImage(@RequestParam("editormd-image-file") MultipartFile multipartFile, HttpSession session) {
        Map<String, Object> res = new HashMap<>();
        Integer uid = (Integer) session.getAttribute("userid");
        UserInfo userInfo = userInfoMapper.selectById(uid);
        res.put("message", "未知原因上传失败啦qwq");
        res.put("success", "0");
        if (userInfo == null) return res;
        if (imageUploadService.uploadPhoto(userInfo, multipartFile)) {
            res.put("success", 1);
            res.put("url", "../user/" + userInfo.getUsername() + "/img/" + multipartFile.getOriginalFilename());
        }
        return res;
    }
}
