package com.stu.nebulablog.service.faq;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.stu.nebulablog.mapper.QMapper;
import com.stu.nebulablog.module.Q;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionDataGetService {
    @Autowired
    QMapper qMapper;
    public Q doGetQuestion(Integer qid){
        return qMapper.selectById(qid);
    }
}
