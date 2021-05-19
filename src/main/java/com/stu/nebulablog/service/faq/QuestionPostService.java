package com.stu.nebulablog.service.faq;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.stu.nebulablog.mapper.QMapper;
import com.stu.nebulablog.module.Q;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionPostService {
    @Autowired
    private QMapper qMapper;

    public boolean doQPost(Q q) {
        QueryWrapper<Q> qQueryWrapper = new QueryWrapper<>();
        qQueryWrapper.eq("uid", q.getUid());
        qQueryWrapper.eq("title", q.getTitle());
        if (qMapper.selectOne(qQueryWrapper) == null && qMapper.insert(q) != 0) return true;
        return false;
    }
}
