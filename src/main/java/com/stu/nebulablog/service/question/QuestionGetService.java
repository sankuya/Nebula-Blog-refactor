package com.stu.nebulablog.service.question;

import com.stu.nebulablog.mapper.QuestionMapper;
import com.stu.nebulablog.module.entity.Question;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class QuestionGetService {
    private final QuestionMapper questionMapper;

    @Cacheable(cacheNames = "question", key = "#qid")
    public Question doGetQuestion(Integer qid) {
        return questionMapper.selectById(qid);
    }
}
