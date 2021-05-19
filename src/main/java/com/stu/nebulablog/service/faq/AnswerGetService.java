package com.stu.nebulablog.service.faq;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.stu.nebulablog.mapper.AMapper;
import com.stu.nebulablog.module.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerGetService {
    @Autowired
    private AMapper aMapper;
    public List<A> doGetA(Integer qid){
        QueryWrapper<A>aQueryWrapper=new QueryWrapper<>();
        aQueryWrapper.eq("qid",qid).orderByAsc("aid");
        return aMapper.selectList(aQueryWrapper);
    }
}
