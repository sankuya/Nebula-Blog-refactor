package com.stu.nebulablog.service.login;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.stu.nebulablog.mapper.UserDetailMapper;
import com.stu.nebulablog.mapper.UserInfoMapper;
import com.stu.nebulablog.module.UserDetail;
import com.stu.nebulablog.module.UserInfo;
import com.stu.nebulablog.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class RegistService {
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

    public String doRegist(UserInfo userRegistVO) {
        userRegistVO.setPassword(passwordUtil.passwordEncoder(userRegistVO.getPassword()));
        QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
        userInfoQueryWrapper
                .eq("username", userRegistVO.getUsername())
                .or()
                .eq("mail", userRegistVO.getMail())
                .or()
                .eq("tel", userRegistVO.getTel());
        UserInfo userInfo = userInfoMapper.selectOne(userInfoQueryWrapper);
        if (userInfo != null) {
            return "该用户名已存在";
        }
        userInfo = userRegistVO;
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
                return "注册成功";
            } catch (IOException e) {
                e.printStackTrace();
                return "注册成功可是初始化头像失败了qwq";
            }
        }
        return "未知错误";
    }
}
