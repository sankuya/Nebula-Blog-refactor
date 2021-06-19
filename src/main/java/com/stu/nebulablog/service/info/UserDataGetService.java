package com.stu.nebulablog.service.info;

import com.stu.nebulablog.mapper.UserDetailMapper;
import com.stu.nebulablog.mapper.UserInfoMapper;
import com.stu.nebulablog.module.entity.UserDetail;
import com.stu.nebulablog.module.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserDataGetService {
    @Autowired
    private UserDetailMapper userDetailMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;

    public Map<String, Object> doGetUserData(Integer uid) {
        QueryWrapper<UserDetail> userDetailQueryWrapper = new QueryWrapper<>();
        userDetailQueryWrapper.eq("uid", uid);
        try {
            Map<String, Object> res = userDetailMapper.selectMaps(userDetailQueryWrapper).get(0);
            UserInfo userInfo = userInfoMapper.selectById(uid);
            res.put("userid",userInfo.getUid());
            res.put("mail", userInfo.getMail());
            res.put("tel", userInfo.getTel());
            res.put("username", userInfo.getUsername());
            return res;
        } catch (Exception e) {
            return null;
        }
    }

}
