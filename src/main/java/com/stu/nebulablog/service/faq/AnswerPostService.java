package com.stu.nebulablog.service.faq;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.stu.nebulablog.mapper.AMapper;
import com.stu.nebulablog.mapper.QMapper;
import com.stu.nebulablog.module.entity.A;
import com.stu.nebulablog.module.entity.Q;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerPostService {
    @Autowired
    private AMapper aMapper;
    @Autowired
    private QMapper qMapper;
    public boolean doAnswerPost(A answer){
        answer.setAid(null);
        UpdateWrapper<Q>qUpdateWrapper=new UpdateWrapper<>();
        qUpdateWrapper.setSql("answer=answer+1");
        qUpdateWrapper.eq("q_id",answer.getQid());
        if(aMapper.insert(answer)!=0&&qMapper.update(null,qUpdateWrapper)!=0)return true;
        return false;
    }
}
