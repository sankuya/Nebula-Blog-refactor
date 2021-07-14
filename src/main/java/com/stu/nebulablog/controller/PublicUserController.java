package com.stu.nebulablog.controller;

import com.stu.nebulablog.module.ResponseData;
import com.stu.nebulablog.module.UserVO;
import com.stu.nebulablog.module.entity.UserInfo;
import com.stu.nebulablog.service.user.UserGetService;
import com.stu.nebulablog.service.login.LoginService;
import com.stu.nebulablog.service.login.RegisterService;
import com.stu.nebulablog.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    private UserGetService userGetService;

    @PostMapping("login")
    ResponseData login(@RequestBody UserVO userVO, HttpSession httpSession, HttpServletResponse httpServletResponse) {
        int uid = loginService.doLogin(userVO);
        if (uid == -1) {
            return ResponseData.fail();
        }
        httpSession.setAttribute("uid", uid);
        Cookie usernameCookie = new Cookie("username", String.valueOf(uid));
        Cookie passwordCookie = new Cookie("password", passwordUtil.passwordEncoder(String.valueOf(uid)));
        httpServletResponse.addCookie(usernameCookie);
        httpServletResponse.addCookie(passwordCookie);
        return ResponseData.success();
    }

    @PostMapping("register")
    ResponseData register(@RequestBody UserInfo userRegisterVO) {
        ResponseData responseData = new ResponseData();userRegisterVO.setUid(null);
        if(registerService.doRegister(userRegisterVO)){
            return ResponseData.success();
        }else{
            return ResponseData.fail();
        }
    }

    @PostMapping("logout")
    public ResponseData logout(HttpSession session, HttpServletResponse httpServletResponse) {
        if (session.getAttribute("uid") != null) session.removeAttribute("uid");
        Cookie usernameCookie = new Cookie("username", null);
        Cookie passwordCookie = new Cookie("password", null);
        usernameCookie.setMaxAge(0);
        passwordCookie.setMaxAge(0);
        httpServletResponse.addCookie(usernameCookie);
        httpServletResponse.addCookie(passwordCookie);
        return ResponseData.success();
    }

    @GetMapping("getUserById")
    public ResponseData getUserDataByID(@RequestParam int uid) {
        Map<String, Object> data = userGetService.doGetUser(uid);
        if (data == null) {
            return ResponseData.fail();
        } else {
            ResponseData responseData=ResponseData.success();
            responseData.setData(data);
            return responseData;
        }
    }
}
