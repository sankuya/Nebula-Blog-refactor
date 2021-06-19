package com.stu.nebulablog.controller;

import com.stu.nebulablog.module.ResponseData;
import com.stu.nebulablog.module.UserVO;
import com.stu.nebulablog.module.entity.UserInfo;
import com.stu.nebulablog.service.info.UserDataGetService;
import com.stu.nebulablog.service.login.LoginService;
import com.stu.nebulablog.service.login.RegisterService;
import com.stu.nebulablog.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/public/user")
public class PublicUserController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private PasswordUtil passwordUtil;
    @Autowired
    private RegisterService registerService;
    @Autowired
    private UserDataGetService userDataGetService;
    @PostMapping("login")
    ResponseData login(@RequestBody UserVO userVO, HttpSession httpSession, HttpServletResponse httpServletResponse) {
        ResponseData responseData = new ResponseData();
        int uid = loginService.doLogin(userVO);
        if (uid == -1) {
            responseData.setCode(101);
            responseData.setMsg("账号或密码错误");
            return responseData;
        }
        responseData.setCode(100);
        responseData.setMsg("登录成功");
        httpSession.setAttribute("uid", uid);
        Cookie usernameCookie = new Cookie("username", String.valueOf(uid));
        Cookie passwordCookie = new Cookie("password", passwordUtil.passwordEncoder(String.valueOf(uid)));
        httpServletResponse.addCookie(usernameCookie);
        httpServletResponse.addCookie(passwordCookie);
        return responseData;
    }
    @PostMapping("register")
    ResponseData register(@RequestBody @Validated UserInfo userRegisterVO) {
        ResponseData responseData=new ResponseData();
        int code=registerService.doRegister(userRegisterVO);
        responseData.setCode(code);
        if(code==100)responseData.setMsg("注册成功");
        if(code==101)responseData.setMsg("用户已存在");
        if(code==102)responseData.setMsg("未知错误");
        return responseData;
    }
    @PostMapping("logout")
    public ResponseData logout(HttpSession session, HttpServletResponse httpServletResponse) {
        ResponseData responseData=new ResponseData();
        if (session.getAttribute("uid") != null) session.removeAttribute("uid");
        Cookie usernameCookie = new Cookie("username", null);
        Cookie passwordCookie = new Cookie("password", null);
        usernameCookie.setMaxAge(0);
        passwordCookie.setMaxAge(0);
        httpServletResponse.addCookie(usernameCookie);
        httpServletResponse.addCookie(passwordCookie);
        responseData.setCode(100);
        responseData.setMsg("ok");
        return responseData;
    }
    @PostMapping("getUserDataByID")
    public ResponseData getUserDataByID(@Validated @RequestBody Map<String, String> src) {
        Integer uid = Integer.valueOf(src.get("uid"));
        ResponseData responseData = new ResponseData();
        Map<String, Object> data = userDataGetService.doGetUserData(uid);
        if (data == null) {
            responseData.setCode(101);
            responseData.setMsg("uid错误");
        }else{
            responseData.setCode(100);
            responseData.setData(data);
        }
        return responseData;
    }
}
