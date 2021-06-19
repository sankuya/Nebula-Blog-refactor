package com.stu.nebulablog.service.faq;

import com.stu.nebulablog.mapper.QMapper;
import com.stu.nebulablog.module.entity.Q;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionGetService {
    @Autowired
    QMapper qMapper;
    public Q doGetQuestion(Integer qid){
        return qMapper.selectById(qid);
    }
}
