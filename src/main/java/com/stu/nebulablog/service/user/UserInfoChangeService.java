package com.stu.nebulablog.service.user;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.stu.nebulablog.mapper.AnswerMapper;
import com.stu.nebulablog.mapper.ArticleMapper;
import com.stu.nebulablog.mapper.QuestionMapper;
import com.stu.nebulablog.mapper.UserDetailMapper;
import com.stu.nebulablog.module.entity.Answer;
import com.stu.nebulablog.module.entity.Article;
import com.stu.nebulablog.module.entity.Question;
import com.stu.nebulablog.module.entity.UserDetail;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserInfoChangeService {
    private final UserDetailMapper userDetailMapper;

    @Transactional
    @Caching(evict = {
            @CacheEvict(cacheNames = "user", key = "#userDetail.uid"),
            @CacheEvict(cacheNames = "userDetail", key = "#userDetail.uid")
    })
    public boolean doInfoChange(UserDetail userDetail) {
        return userDetailMapper.updateById(userDetail) != 0;
    }
}
