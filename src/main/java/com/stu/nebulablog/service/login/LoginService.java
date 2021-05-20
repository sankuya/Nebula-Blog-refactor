package com.stu.nebulablog.service.login;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.stu.nebulablog.mapper.UserInfoMapper;
import com.stu.nebulablog.module.UserInfo;
import com.stu.nebulablog.module.UserVO;
import com.stu.nebulablog.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class LoginService {
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private PasswordUtil passwordUtil;
    public int doLogin(UserVO userVO) {
        userVO.setPassword(passwordUtil.passwordEncoder(userVO.getPassword()));
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("username", userVO.getUsername())
                .or()
                .eq("tel", userVO.getUsername())
                .or()
                .eq("mail", userVO.getUsername())
        ;
        UserInfo userInfo = userInfoMapper.selectOne(queryWrapper);
        if (userInfo!=null&&userInfo.getPassword().equals(userVO.getPassword())) {
            return userInfo.getUid();
        }
        return -1;
    }
}
