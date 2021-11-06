package com.stu.nebulablog.service.question;

import com.stu.nebulablog.mapper.QuestionMapper;
import com.stu.nebulablog.module.entity.Question;
import com.stu.nebulablog.service.user.UserDetailGetService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class QuestionGetService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserDetailGetService userDetailGetService;
    @Autowired
    private QuestionGetService questionGetService;

    @Cacheable(cacheNames = "question", key = "#questionId")
    public Question doGetQuestion(Integer questionId) {
        return questionMapper.selectById(questionId);
    }

    public Question getQuestion(Integer questionId) {
        Question question = questionGetService.doGetQuestion(questionId);
        question.setAuthor(userDetailGetService.getUserDetail(question.getUid()).getNickname());
        return question;
    }
}
