package com.stu.nebulablog.service.interceptor;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.stu.nebulablog.mapper.UserInfoMapper;
import com.stu.nebulablog.module.entity.UserInfo;
import com.stu.nebulablog.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Service
public class LoginInterceptorService implements HandlerInterceptor {
    @Autowired
    private PasswordUtil passwordUtil;
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        HttpSession session = request.getSession();
        if (session.getAttribute("uid") != null) {
            return true;
        }
        Cookie[] cookies = request.getCookies();
        String username = null;
        String password = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("username")) username = cookie.getValue();
                if (cookie.getName().equals("password")) password = cookie.getValue();
            }
            if (username != null && password != null && passwordUtil.passwordEncoder(username).equals(password)) {
                LambdaQueryWrapper<UserInfo>userInfoLambdaQueryWrapper=new LambdaQueryWrapper<>();
                userInfoLambdaQueryWrapper
                        .eq(UserInfo::getUsername,username)
                        .select(UserInfo::getUid);
                int uid = userInfoMapper.selectOne(userInfoLambdaQueryWrapper).getUid();
                session.setAttribute("uid", uid);
                return true;
            }
        }
        response.setStatus(403);
        return false;
    }
}
