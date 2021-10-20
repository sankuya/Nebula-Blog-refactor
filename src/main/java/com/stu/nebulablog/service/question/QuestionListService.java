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
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class QuestionListService {
    private final QuestionMapper questionMapper;
    private final PageToMapUtil<Question> qPageToMapUtil;
    private static final int SUMMARY_SIZE = 255;

    @Cacheable(cacheNames = "questionList")
    public PageDataVO<Question> list(int size, int page) {
        return list(null, size, page);
    }

    public PageDataVO<Question> list(@Nullable Integer uid, int size, int page) {
        Page<Question> qPage = new Page<>(page, size);
        questionMapper.selectPage(qPage,
                new QueryWrapper<Question>()
                        .select("title", "author", "answer_num", "status", "date",
                                "question_id", "uid", "LEFT(content," + SUMMARY_SIZE + ") AS summary")
                        .lambda()
                        .eq(uid != null, Question::getUid, uid)
                        .orderByDesc(Question::getQuestionId));
        return qPageToMapUtil.getMapFromPageWithPages(qPage);
    }
}
