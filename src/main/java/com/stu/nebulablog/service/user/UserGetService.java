package com.stu.nebulablog.service.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.stu.nebulablog.mapper.UserDetailMapper;
import com.stu.nebulablog.mapper.UserInfoMapper;
import com.stu.nebulablog.module.entity.UserDetail;
import com.stu.nebulablog.module.entity.UserInfo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class UserGetService {
    private final UserDetailMapper userDetailMapper;
    private final UserInfoMapper userInfoMapper;

    @Cacheable(cacheNames = "user")
    public Map<String, Object> doGetUser(int uid) {
        try {
            Map<String, Object> res = userDetailMapper.selectMaps(
                    new LambdaQueryWrapper<UserDetail>()
                            .eq(UserDetail::getUid, uid)
            ).get(0);
            UserInfo userInfo = userInfoMapper.selectById(uid);
            res.put("uid", userInfo.getUid());
            res.put("mail", userInfo.getMail());
            res.put("tel", userInfo.getTel());
            res.put("username", userInfo.getUsername());
            return res;
        } catch (Exception e) {
            return null;
        }
    }

}
