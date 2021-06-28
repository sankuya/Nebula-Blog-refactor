package com.stu.nebulablog.service.question;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.stu.nebulablog.mapper.QuestionMapper;
import com.stu.nebulablog.mapper.UserInfoMapper;
import com.stu.nebulablog.module.entity.Question;
import com.stu.nebulablog.module.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@Service
public class QuestionPostService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;

    @CacheEvict(cacheNames = "questionList", allEntries = true)
    public boolean doPost(Question question) {
        LambdaQueryWrapper<UserInfo> userInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userInfoLambdaQueryWrapper
                .select(UserInfo::getUsername)
                .eq(UserInfo::getUid, question.getUid());
        question.setAuthor(userInfoMapper.selectOne(userInfoLambdaQueryWrapper).getUsername());
        LambdaQueryWrapper<Question> questionLambdaQueryWrapper = new LambdaQueryWrapper<>();
        questionLambdaQueryWrapper
                .eq(Question::getUid, question.getUid())
                .eq(Question::getTitle, question.getTitle());
        if (questionMapper.selectCount(questionLambdaQueryWrapper) == 0 && questionMapper.insert(question) != 0)
            return true;
        return false;
    }
}
