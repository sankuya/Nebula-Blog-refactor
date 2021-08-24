package com.stu.nebulablog.service.answer;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.stu.nebulablog.mapper.AnswerMapper;
import com.stu.nebulablog.module.entity.Answer;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AnswerListService {
    private final AnswerMapper answerMapper;

    @Cacheable(cacheNames = "answerList", key = "#qid")
    public List<Answer> doList(Integer qid) {
        QueryWrapper<Answer> aQueryWrapper = new QueryWrapper<>();
        aQueryWrapper.eq("qid", qid).orderByAsc("aid");
        return answerMapper.selectList(aQueryWrapper);
    }
}
