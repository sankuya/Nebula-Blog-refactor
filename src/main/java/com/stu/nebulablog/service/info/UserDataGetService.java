package com.stu.nebulablog.service.info;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.stu.nebulablog.mapper.UserDetailMapper;
import com.stu.nebulablog.mapper.UserInfoMapper;
import com.stu.nebulablog.module.UserDetail;
import com.stu.nebulablog.module.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserDataGetService {
    @Autowired
    private UserDetailMapper userDetailMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;

    public Map<String, Object> doGetUserData(Integer uid) {
        try {
            Map<String, Object> res = new HashMap<>();
            UserDetail userDetail = userDetailMapper.selectById(uid);
            UserInfo userInfo = userInfoMapper.selectById(uid);
            res.put("motto", userDetail.getMotto());
            res.put("QQ", userDetail.getQQ());
            res.put("hobby", userDetail.getHobby());
            res.put("location", userDetail.getLocation());
            res.put("blogname", userDetail.getBlogname());
            res.put("nickname", userDetail.getNickname());
            res.put("userid", userInfo.getUid());
            res.put("mail", userInfo.getMail());
            res.put("tel", userInfo.getTel());
            res.put("username", userInfo.getUsername());
            res.put("gender", userDetail.getGender());
            res.forEach((k, v) -> {
                if (v == null) {
                    res.put(k,"");
                }
            });
            return res;
        } catch (Exception e) {
            return null;
        }
    }

}
