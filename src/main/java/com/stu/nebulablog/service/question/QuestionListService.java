package com.stu.nebulablog.service.question;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stu.nebulablog.mapper.QuestionMapper;
import com.stu.nebulablog.module.entity.Question;
import com.stu.nebulablog.module.vo.PageDataVO;
import com.stu.nebulablog.service.user.UserDetailGetService;
import com.stu.nebulablog.utils.PageToMapUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class QuestionListService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private PageToMapUtil<Question> questionPageToMapUtil;
    @Autowired
    private UserDetailGetService userDetailGetService;
    private static final int SUMMARY_SIZE = 255;
    @Autowired
    private QuestionListService questionListService;

    @Cacheable(cacheNames = "questionList")
    public PageDataVO<Question> listQuestion(@Nullable Integer uid, int size, int page) {
        PageDataVO<Question> questionPageDataVO = questionListService.doListQuestion(uid, size, page);
        questionPageDataVO.getDetail().forEach(question ->
                question.setAuthor(userDetailGetService.getUserDetail(question.getUid()).getNickname()));
        return questionPageDataVO;
    }

    public PageDataVO<Question> doListQuestion(@Nullable Integer uid, int size, int page) {
        Page<Question> qPage = new Page<>(page, size);
        questionMapper.selectPage(qPage,
                new QueryWrapper<Question>()
                        .select("title", "answer_num", "status", "date",
                                "question_id", "uid", "LEFT(content," + SUMMARY_SIZE + ") AS summary")
                        .lambda()
                        .eq(uid != null, Question::getUid, uid)
                        .orderByDesc(Question::getQuestionId));
        return questionPageToMapUtil.getMapFromPageWithPages(qPage);
    }
}
