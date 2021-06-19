package com.stu.nebulablog.service.login;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.stu.nebulablog.mapper.UserDetailMapper;
import com.stu.nebulablog.mapper.UserInfoMapper;
import com.stu.nebulablog.module.entity.UserDetail;
import com.stu.nebulablog.module.entity.UserInfo;
import com.stu.nebulablog.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class RegisterService {
    private static String preUrl;
    private static String defaultProfilePhotoPath;
    private static File defaultProfilePhotoFile;
    @Autowired
    private UserDetailMapper userDetailMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private PasswordUtil passwordUtil;

    @Value("${user.photo.preUrl}")
    public void setPreUrl(String preUrl) {
        this.preUrl = preUrl;
        this.defaultProfilePhotoPath = preUrl + "defaultProfilePhoto.jpg";
        this.defaultProfilePhotoFile = new File(defaultProfilePhotoPath);
    }

    public int doRegister(UserInfo userRegisterVO) {
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
            return 201;//"该用户名已存在"
        }
        userInfo = userRegisterVO;
        UserDetail userDetail = new UserDetail();
        userDetail.setBlogname(userInfo.getUsername() + "的小天地");
        userDetail.setGender("男");
        userDetail.setNickname(userInfo.getUsername());
        if (userInfoMapper.insert(userInfo) == 1 && userDetailMapper.insert(userDetail) == 1) {
            String imgDirectoryPath = preUrl + userInfo.getUsername();
            File imgDirectory = new File(imgDirectoryPath+"/img");
            imgDirectory.mkdirs();
            File imgFile = new File(imgDirectoryPath, "ProfilePhoto.jpg");
            try {
                Files.copy(defaultProfilePhotoFile.toPath(), imgFile.toPath());
                return 200;//"注册成功"
            } catch (IOException e) {
                e.printStackTrace();
                return 203;//"注册成功可是初始化头像失败了qwq";
            }
        }
        return 202;//"未知错误";
    }
}
