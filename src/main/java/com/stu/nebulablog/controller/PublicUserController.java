package com.stu.nebulablog.controller;

import com.stu.nebulablog.module.ResponseData;
import com.stu.nebulablog.module.vo.UserVO;
import com.stu.nebulablog.module.entity.UserInfo;
import com.stu.nebulablog.service.user.UserGetService;
import com.stu.nebulablog.service.login.LoginService;
import com.stu.nebulablog.service.login.RegisterService;
import com.stu.nebulablog.utils.PasswordUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/public/user")
@AllArgsConstructor
public class PublicUserController {
    private final LoginService loginService;
    private final PasswordUtil passwordUtil;
    private final RegisterService registerService;
    private final UserGetService userGetService;
    private static final int COOKIE_EXPIRED = 60 * 60 * 12 * 7;

    @PostMapping("login")
    ResponseData login(@Nullable @RequestBody UserVO userVO, HttpSession httpSession, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        int uid = Optional
                .ofNullable(userVO)
                .map(notNullUerVO -> loginService.doLogin(notNullUerVO))
                .orElseGet(() -> {
                    Cookie[] cookies = httpServletRequest.getCookies();
                    String username = null, password = null;
                    for (Cookie cookie : cookies) {
                        if (cookie.getName().equals("username")) username = cookie.getValue();
                        if (cookie.getName().equals("password")) password = cookie.getValue();
                    }
                    if (username != null && password != null && passwordUtil.passwordEncoder(username).equals(password))
                        return Integer.valueOf(username);
                    else return -1;
                });
        if (uid == -1) {
            return ResponseData.fail();
        }
        httpSession.setAttribute("uid", uid);
        Cookie usernameCookie = new Cookie("username", String.valueOf(uid));
        Cookie passwordCookie = new Cookie("password", passwordUtil.passwordEncoder(String.valueOf(uid)));
        usernameCookie.setMaxAge(COOKIE_EXPIRED);
        passwordCookie.setMaxAge(COOKIE_EXPIRED);
        httpServletResponse.addCookie(usernameCookie);
        httpServletResponse.addCookie(passwordCookie);
        return ResponseData.success();
    }

    @PostMapping("register")
    ResponseData register(@RequestBody UserInfo userRegisterVO) {
        userRegisterVO.setUid(null);
        if (registerService.doRegister(userRegisterVO)) {
            return ResponseData.success();
        } else {
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
            ResponseData responseData = ResponseData.success();
            responseData.setData(data);
            return responseData;
        }
    }
}
