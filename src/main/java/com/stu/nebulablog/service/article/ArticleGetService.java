package com.stu.nebulablog.service.article;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.stu.nebulablog.mapper.ArticleMapper;
import com.stu.nebulablog.mapper.UserDetailMapper;
import com.stu.nebulablog.module.entity.Article;
import com.stu.nebulablog.module.entity.UserDetail;
import com.stu.nebulablog.service.user.UserDetailGetService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ArticleGetService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private UserDetailGetService userDetailGetService;
    @Autowired
    private ArticleGetService articleGetService;

    @Cacheable(cacheNames = "article", key = "#articleId")
    public Article doGetArticleDetail(int articleId) {
        return articleMapper.selectById(articleId);
    }

    public Article getArticleDetail(int articleId) {
        Article article = articleGetService.doGetArticleDetail(articleId);
        article.setAuthor(userDetailGetService.getUserDetail(article.getUid()).getNickname());
        return article;
    }
}
