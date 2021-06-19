package com.stu.nebulablog.service.answer;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.stu.nebulablog.mapper.AnswerMapper;
import com.stu.nebulablog.mapper.QuestionMapper;
import com.stu.nebulablog.module.entity.Answer;
import com.stu.nebulablog.module.entity.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerPostService {
    @Autowired
    private AnswerMapper answerMapper;
    @Autowired
    private QuestionMapper questionMapper;
    public boolean doAnswerPost(Answer answer){
        answer.setAid(null);
        UpdateWrapper<Question>qUpdateWrapper=new UpdateWrapper<>();
        qUpdateWrapper.setSql("answer=answer+1");
        qUpdateWrapper.eq("q_id",answer.getQid());
        if(answerMapper.insert(answer)!=0&& questionMapper.update(null,qUpdateWrapper)!=0)return true;
        return false;
    }
}
