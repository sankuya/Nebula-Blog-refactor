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
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class QuestionSearchService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private PageToMapUtil<Question> pageToMapUtil;
    @Autowired
    private UserDetailGetService userDetailGetService;
    private static final int SUMMARY_SIZE = 255;
    @Autowired
    private QuestionSearchService questionSearchService;//获取代理后的当前对象，否则缓存失败

    public PageDataVO<Question> searchQuestion(String keyword, int page, int size) {
        PageDataVO<Question> questionPageDataVO = questionSearchService.doSearchQuestion(keyword, page, size);
        questionPageDataVO.getDetail().forEach(question ->
                question.setAuthor(userDetailGetService.getUserDetail(question.getUid()).getNickname()));
        return questionPageDataVO;
    }

    @Cacheable(cacheNames = "questionList")
    public PageDataVO<Question> doSearchQuestion(String keyword, int page, int size) {
        Page<Question> qPage = new Page<>(page, size);
        LambdaQueryWrapper<Question> questionLambdaQueryWrapper = new QueryWrapper<Question>()
                .select("title", "answer_num", "status", "date",
                        "question_id", "uid", "LEFT(content," + SUMMARY_SIZE + ") AS summary")
                .lambda()
                .like(Question::getTitle, keyword)
                .or()
                .like(Question::getContent, keyword)
                .orderByDesc(Question::getQuestionId);
        return pageToMapUtil.getMapFromPageWithPages(questionMapper.selectPage(qPage, questionLambdaQueryWrapper));
    }
}
