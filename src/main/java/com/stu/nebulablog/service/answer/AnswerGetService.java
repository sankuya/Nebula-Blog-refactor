package com.stu.nebulablog.service.answer;

import com.stu.nebulablog.mapper.AnswerMapper;
import com.stu.nebulablog.module.entity.Answer;
import com.stu.nebulablog.service.user.UserDetailGetService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class AnswerGetService {
    @Autowired
    private AnswerMapper answerMapper;
    @Autowired
    private UserDetailGetService userDetailGetService;
    @Autowired
    private AnswerGetService answerGetService;

    public Answer getAnswer(long answerId) {
        Answer answer = answerGetService.doGetAnswer(answerId);
        answer.setAuthor(userDetailGetService.getUserDetail(answer.getUid()).getNickname());
        return answer;
    }

    @Cacheable(cacheNames = "answer")
    public Answer doGetAnswer(long answerId) {
        return answerMapper.selectById(answerId);
    }
}
