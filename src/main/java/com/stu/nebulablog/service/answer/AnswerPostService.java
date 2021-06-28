package com.stu.nebulablog.service.answer;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.stu.nebulablog.mapper.AnswerMapper;
import com.stu.nebulablog.mapper.QuestionMapper;
import com.stu.nebulablog.module.entity.Answer;
import com.stu.nebulablog.module.entity.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@Service
public class AnswerPostService {
    @Autowired
    private AnswerMapper answerMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Caching(evict = {
            @CacheEvict(cacheNames = "answerList",key = "#answer.questionId"),
            @CacheEvict(cacheNames = "questionList",allEntries = true),
            @CacheEvict(cacheNames = "question",key = "#answer.questionId")
    })
    public boolean doPost(Answer answer){
        answer.setAnswerId(null);
        LambdaUpdateWrapper<Question>questionLambdaUpdateWrapper=new LambdaUpdateWrapper<>();
        questionLambdaUpdateWrapper
                .setSql("answer=answer+1")
                .eq(Question::getQuestionId,answer.getQuestionId());
        if(answerMapper.insert(answer)!=0&& questionMapper.update(null,questionLambdaUpdateWrapper)!=0)return true;
        return false;
    }
}
