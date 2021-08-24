package com.stu.nebulablog.service.user;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.stu.nebulablog.mapper.AnswerMapper;
import com.stu.nebulablog.mapper.ArticleMapper;
import com.stu.nebulablog.mapper.QuestionMapper;
import com.stu.nebulablog.mapper.UserDetailMapper;
import com.stu.nebulablog.module.entity.Answer;
import com.stu.nebulablog.module.entity.Article;
import com.stu.nebulablog.module.entity.Question;
import com.stu.nebulablog.module.entity.UserDetail;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserInfoChangeService {
    private final UserDetailMapper userDetailMapper;
    private final ArticleMapper articleMapper;
    private final QuestionMapper questionMapper;
    private final AnswerMapper answerMapper;

    @Transactional
    @Caching(evict = {
            @CacheEvict(cacheNames = "user", key = "#userDetail.uid"),
            @CacheEvict(cacheNames = "articleList", allEntries = true),
            @CacheEvict(cacheNames = "article", allEntries = true),
            @CacheEvict(cacheNames = "questionList", allEntries = true),
            @CacheEvict(cacheNames = "question", allEntries = true),
            @CacheEvict(cacheNames = "answerList", allEntries = true)
    })
    public boolean doInfoChange(UserDetail userDetail) {
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
        articleMapper.update(article,
                new LambdaUpdateWrapper<Article>()
                        .eq(Article::getUid, article.getUid()));
        questionMapper.update(question,
                new LambdaUpdateWrapper<Question>()
                        .eq(Question::getUid, question.getUid()));
        answerMapper.update(answer,
                new LambdaUpdateWrapper<Answer>()
                        .eq(Answer::getUid, answer.getUid()));
        return true;
    }
}
