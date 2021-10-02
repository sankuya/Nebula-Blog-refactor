package com.stu.nebulablog.service.answer;

import com.stu.nebulablog.mapper.AnswerMapper;
import com.stu.nebulablog.module.entity.Answer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AnswerGetService {
    private AnswerMapper answerMapper;

    public Answer get(long answerId) {
        return answerMapper.selectById(answerId);
    }
}
