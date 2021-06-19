package com.stu.nebulablog.old_controller;

import com.stu.nebulablog.module.entity.UserInfo;
import com.stu.nebulablog.module.UserVO;
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
import java.util.HashMap;
import java.util.Map;

//@RequestMapping("login")
@RestController
public class LoginController {
    @Autowired
    LoginService loginService;
    @Autowired
    RegisterService registerService;
    @Autowired
    PasswordUtil passwordUtil;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody UserVO userVO, HttpSession httpSession, HttpServletResponse httpServletResponse) {
        Map<String, String> res = new HashMap<>();
        int uid = loginService.doLogin(userVO);
        if (uid == -1) {
            res.put("status", "账号或密码错误");
            return res;
        }
        res.put("status", "登陆成功");
        res.put("userid", String.valueOf(uid));
        httpSession.setAttribute("userid", uid);
        Cookie usernameCookie = new Cookie("username", String.valueOf(uid));
        Cookie passwordCookie = new Cookie("password", passwordUtil.passwordEncoder(String.valueOf(uid)));
        httpServletResponse.addCookie(usernameCookie);
        httpServletResponse.addCookie(passwordCookie);
        return res;
    }

    @PostMapping("/regist")
    public String regist(@RequestBody @Validated UserInfo userRegistVO) {
        return String.valueOf(registerService.doRegister(userRegistVO));
    }
}
