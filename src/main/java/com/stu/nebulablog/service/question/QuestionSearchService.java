package com.stu.nebulablog.service.question;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stu.nebulablog.mapper.QuestionMapper;
import com.stu.nebulablog.module.entity.Question;
import com.stu.nebulablog.utils.PageToMapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class QuestionSearchService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private PageToMapUtil pageToMapUtil;

    @Cacheable(cacheNames = "questionList")
    public Map<String, Object> doQuestionSearch(String keyword, int page, int size) {
        Page<Question> qPage = new Page<>(page, size);
        LambdaQueryWrapper<Question> questionLambdaQueryWrapper = new LambdaQueryWrapper<>();
        questionLambdaQueryWrapper
                .select(Question::getQuestionId, Question::getTitle, Question::getAuthor, Question::getDate, Question::getAnswer, Question::getStatus)
                .like(Question::getTitle, keyword)
                .or()
                .like(Question::getContent, keyword)
                .orderByDesc(Question::getQuestionId);
        return pageToMapUtil.getMapFromPageWithPages(questionMapper.selectPage(qPage, questionLambdaQueryWrapper));
    }
}
