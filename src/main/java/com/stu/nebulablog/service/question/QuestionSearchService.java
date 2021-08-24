package com.stu.nebulablog.service.question;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stu.nebulablog.mapper.QuestionMapper;
import com.stu.nebulablog.module.entity.Question;
import com.stu.nebulablog.module.vo.PageDataVO;
import com.stu.nebulablog.utils.PageToMapUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class QuestionSearchService {
    private final QuestionMapper questionMapper;
    private final PageToMapUtil<Question> pageToMapUtil;

    @Cacheable(cacheNames = "questionList")
    public PageDataVO<Question> doQuestionSearch(String keyword, int page, int size) {
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
