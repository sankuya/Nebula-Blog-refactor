package com.stu.nebulablog.service.answer;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.stu.nebulablog.mapper.AnswerMapper;
import com.stu.nebulablog.module.entity.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerGetService {
    @Autowired
    private AnswerMapper answerMapper;
    public List<Answer> doGetA(Integer qid){
        QueryWrapper<Answer>aQueryWrapper=new QueryWrapper<>();
        aQueryWrapper.eq("qid",qid).orderByAsc("aid");
        return answerMapper.selectList(aQueryWrapper);
    }
}
