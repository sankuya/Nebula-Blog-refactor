package com.stu.nebulablog.service.answer;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stu.nebulablog.mapper.AnswerMapper;
import com.stu.nebulablog.module.entity.Answer;
import com.stu.nebulablog.module.entity.Question;
import com.stu.nebulablog.module.vo.PageDataVO;
import com.stu.nebulablog.utils.PageToMapUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class AnswerListService {
    private final AnswerMapper answerMapper;
    private final PageToMapUtil<Answer> answerPageToMapUtil;
    private static final int SUMMARY_SIZE = 255;

    @Cacheable(cacheNames = "answerList", key = "#questionId")
    public PageDataVO<Answer> list(Integer questionId, Integer page, Integer size) {
        Page<Answer> answerPage = new Page<>(page, size);
        answerMapper.selectPage(answerPage, new QueryWrapper<Answer>()
                .select("LEFT(content," + SUMMARY_SIZE + ") AS summary", "answer_id", "uid",
                        "date", "question_id", "author", "username")
                .lambda()
                .eq(questionId != null, Answer::getQuestionId, questionId)
                .orderByDesc(Answer::getAnswerId));
        return answerPageToMapUtil.getMapFromPageWithPages(answerPage);
    }
}
