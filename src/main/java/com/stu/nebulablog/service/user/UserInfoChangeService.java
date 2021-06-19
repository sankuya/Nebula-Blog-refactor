package com.stu.nebulablog.service.user;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.stu.nebulablog.mapper.AnswerMapper;
import com.stu.nebulablog.mapper.ArticleMapper;
import com.stu.nebulablog.mapper.QuestionMapper;
import com.stu.nebulablog.mapper.UserDetailMapper;
import com.stu.nebulablog.module.entity.Answer;
import com.stu.nebulablog.module.entity.Article;
import com.stu.nebulablog.module.entity.Question;
import com.stu.nebulablog.module.entity.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserInfoChangeService {
    @Autowired
    private UserDetailMapper userDetailMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private AnswerMapper answerMapper;

    @Transactional
    public boolean doInfoChange(UserDetail userDetail){
        Article article = new Article();
        Question question = new Question();
        Answer answer = new Answer();
        article.setAuthor(userDetail.getNickname());
        article.setUid(userDetail.getUid());
        question.setAuthor(userDetail.getNickname());
        question.setUid(userDetail.getUid());
        answer.setAuthor(userDetail.getNickname());
        answer.setUid(userDetail.getUid());
        if (userDetailMapper.updateById(userDetail) == 0) return false;
        UpdateWrapper<Article> userDetailUpdateWrapper = new UpdateWrapper<>();
        userDetailUpdateWrapper.eq("uid", article.getUid());
        articleMapper.update(article, userDetailUpdateWrapper);
        UpdateWrapper<Question> qUpdateWrapper = new UpdateWrapper<>();
        qUpdateWrapper.eq("uid", question.getUid());
        questionMapper.update(question, qUpdateWrapper);
        UpdateWrapper<Answer> aUpdateWrapper = new UpdateWrapper<>();
        aUpdateWrapper.eq("uid", answer.getUid());
        answerMapper.update(answer, aUpdateWrapper);
        return true;
    }
}
