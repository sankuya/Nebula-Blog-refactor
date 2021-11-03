package com.stu.nebulablog.service.answer;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.stu.nebulablog.mapper.AnswerMapper;
import com.stu.nebulablog.mapper.QuestionMapper;
import com.stu.nebulablog.module.entity.Answer;
import com.stu.nebulablog.module.entity.Question;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AnswerPostService {
    private final AnswerMapper answerMapper;
    private final QuestionMapper questionMapper;

    @Caching(evict = {
            @CacheEvict(cacheNames = "answerList", allEntries = true),
            @CacheEvict(cacheNames = "questionList", allEntries = true),
            @CacheEvict(cacheNames = "question", key = "#answer.questionId")
    })
    public boolean doPost(Answer answer) {
        answer.setAnswerId(null);
        LambdaUpdateWrapper<Question> questionLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        questionLambdaUpdateWrapper
                .setSql("answer_num=answer_num+1")
                .eq(Question::getQuestionId, answer.getQuestionId());
        return answerMapper.insert(answer) != 0 && questionMapper.update(null, questionLambdaUpdateWrapper) != 0;
    }
}
