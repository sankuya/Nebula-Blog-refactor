package com.stu.nebulablog.service.question;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stu.nebulablog.mapper.QuestionMapper;
import com.stu.nebulablog.module.entity.Question;
import com.stu.nebulablog.utils.PageToMapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class QuestionListService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private PageToMapUtil<Question> qPageToMapUtil;

    public Map<String, Object> doList(int size, int page) {
        Page<Question> qPage = new Page<>(page, size);
        questionMapper.selectPage(qPage, null);
        return qPageToMapUtil.getMapFromPageWithPages(qPage);
    }
}
