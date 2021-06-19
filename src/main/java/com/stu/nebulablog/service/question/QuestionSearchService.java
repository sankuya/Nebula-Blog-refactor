package com.stu.nebulablog.service.question;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stu.nebulablog.mapper.QuestionMapper;
import com.stu.nebulablog.module.entity.Question;
import com.stu.nebulablog.utils.PageToMapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class QuestionSearchService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private PageToMapUtil pageToMapUtil;

    public Map<String, Object> doQuestionSearch(String keyword, int page, int size) {
        Page<Question> qPage = new Page<>(page, size);
        QueryWrapper<Question> qQueryWrapper = new QueryWrapper<>();
        qQueryWrapper
                .select("q_id","title","author","date","answer","status")
                .like("title",keyword)
                .or()
                .like("author",keyword)
                .orderByDesc("q_id");
        return pageToMapUtil.getMapFromPageWithPages(questionMapper.selectPage(qPage, qQueryWrapper));
    }
}
