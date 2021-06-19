package com.stu.nebulablog.service.question;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.stu.nebulablog.mapper.QuestionMapper;
import com.stu.nebulablog.module.entity.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionPostService {
    @Autowired
    private QuestionMapper questionMapper;

    public boolean doPost(Question question) {
        QueryWrapper<Question> qQueryWrapper = new QueryWrapper<>();
        qQueryWrapper.eq("uid", question.getUid());
        qQueryWrapper.eq("title", question.getTitle());
        if (questionMapper.selectOne(qQueryWrapper) == null && questionMapper.insert(question) != 0) return true;
        return false;
    }
}
