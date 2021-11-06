package com.stu.nebulablog.service.user;

import com.stu.nebulablog.mapper.UserDetailMapper;
import com.stu.nebulablog.module.entity.UserDetail;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserDetailGetService {
    private final UserDetailMapper userDetailMapper;

    @Cacheable(cacheNames = "userDetail")
    public UserDetail getUserDetail(int uid) {
        return userDetailMapper.selectById(uid);
    }
}
