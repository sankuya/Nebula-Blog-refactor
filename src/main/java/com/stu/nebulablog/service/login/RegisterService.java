package com.stu.nebulablog.service.login;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.stu.nebulablog.mapper.UserDetailMapper;
import com.stu.nebulablog.mapper.UserInfoMapper;
import com.stu.nebulablog.module.entity.UserDetail;
import com.stu.nebulablog.module.entity.UserInfo;
import com.stu.nebulablog.service.file.HeadPhotoInitService;
import com.stu.nebulablog.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class RegisterService {
    @Autowired
    private UserDetailMapper userDetailMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private PasswordUtil passwordUtil;
    @Autowired
    private HeadPhotoInitService headPhotoInitService;

    public boolean doRegister(UserInfo userRegisterVO) {
        userRegisterVO.setPassword(passwordUtil.passwordEncoder(userRegisterVO.getPassword()));
        QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
        userInfoQueryWrapper
                .eq("username", userRegisterVO.getUsername())
                .or()
                .eq("mail", userRegisterVO.getMail())
                .or()
                .eq("tel", userRegisterVO.getTel());
        UserInfo userInfo = userInfoMapper.selectOne(userInfoQueryWrapper);
        if (userInfo != null) {
            return false;
        }
        userInfo = userRegisterVO;
        UserDetail userDetail = new UserDetail();
        userDetail.setGender("男");
        userDetail.setNickname(userInfo.getUsername());
        if (userInfoMapper.insert(userInfo) == 1 && userDetailMapper.insert(userDetail) == 1) {
            headPhotoInitService.initHeadPhoto(userInfo.getUsername());
            return true;
        }
        return false;
    }
}
