package com.stu.nebulablog.service.question;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
public class QuestionListService {
    private final QuestionMapper questionMapper;
    private final PageToMapUtil<Question> qPageToMapUtil;

    @Cacheable(cacheNames = "questionList")
    public PageDataVO<Question> doList(int size, int page) {
        Page<Question> qPage = new Page<>(page, size);
        questionMapper.selectPage(qPage,
                new LambdaQueryWrapper<Question>()
                        .select(Question::getTitle)
                        .select(Question::getAuthor)
                        .select(Question::getAnswer)
                        .select(Question::getStatus)
                        .select(Question::getDate));
        return qPageToMapUtil.getMapFromPageWithPages(qPage);
    }
}
