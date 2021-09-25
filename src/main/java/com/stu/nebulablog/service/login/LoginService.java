package com.stu.nebulablog.service.login;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.stu.nebulablog.mapper.UserInfoMapper;
import com.stu.nebulablog.module.entity.UserInfo;
import com.stu.nebulablog.module.vo.UserVO;
import com.stu.nebulablog.utils.PasswordUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoginService {
    private final UserInfoMapper userInfoMapper;
    private final PasswordUtil passwordUtil;

    public int doLogin(UserVO userVO) {
        if (userVO.getUsername() == null || userVO.getPassword() == null) return -1;
        userVO.setPassword(passwordUtil.passwordEncoder(userVO.getPassword()));
        UserInfo userInfo = userInfoMapper.selectOne(new LambdaQueryWrapper<UserInfo>()
                .eq(UserInfo::getUsername, userVO.getUsername())
                .or()
                .eq(UserInfo::getTel, userVO.getUsername())
                .or()
                .eq(UserInfo::getMail, userVO.getUsername()));
        if (userInfo != null && userInfo.getPassword().equals(userVO.getPassword())) {
            return userInfo.getUid();
        }
        return -1;
    }
}
