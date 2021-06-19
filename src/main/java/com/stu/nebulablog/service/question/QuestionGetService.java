package com.stu.nebulablog.service.question;

import com.stu.nebulablog.mapper.QuestionMapper;
import com.stu.nebulablog.module.entity.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionGetService {
    @Autowired
    QuestionMapper questionMapper;
    public Question doGetQuestion(Integer qid){
        return questionMapper.selectById(qid);
    }
}
