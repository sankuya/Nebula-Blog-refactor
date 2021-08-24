package com.stu.nebulablog.service.interceptor;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.stu.nebulablog.mapper.UserInfoMapper;
import com.stu.nebulablog.module.entity.UserInfo;
import com.stu.nebulablog.utils.PasswordUtil;
import lombok.AllArgsConstructor;
import org.apache.ibatis.javassist.util.proxy.MethodHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

@Service
@AllArgsConstructor
public class LoginInterceptorService implements HandlerInterceptor {
    private final PasswordUtil passwordUtil;
    private final UserInfoMapper userInfoMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod) ||//捕获的方法不在controller包下放行返回404
                !((HandlerMethod) handler).getMethod().getDeclaringClass().getPackage().getName().startsWith("com.stu.nebulablog.controller")) {
            return true;
        }
        HttpSession session = request.getSession();
        if (session.getAttribute("uid") != null) {//session存在uid字段则用户已登录即放行
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
                LambdaQueryWrapper<UserInfo> userInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
                userInfoLambdaQueryWrapper
                        .eq(UserInfo::getUsername, username)
                        .select(UserInfo::getUid);
                int uid = userInfoMapper.selectOne(userInfoLambdaQueryWrapper).getUid();
                session.setAttribute("uid", uid);
                return true;
            }
        }//未登录且cookie中的username和password字段不能再次登录则返回403
        response.setStatus(403);
        return false;
    }
}
