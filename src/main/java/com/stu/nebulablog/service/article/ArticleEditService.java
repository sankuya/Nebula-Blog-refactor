package com.stu.nebulablog.service.article;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.stu.nebulablog.mapper.ArticleMapper;
import com.stu.nebulablog.module.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@Service
public class ArticleEditService {
    @Autowired
    private ArticleMapper articleMapper;

    @Caching(evict = {
            @CacheEvict(cacheNames = "articleList", allEntries = true),
            @CacheEvict(cacheNames = "article", key = "#article.articleId")})
    public boolean doEditArticle(Article article) {
        LambdaQueryWrapper<Article> articleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        articleLambdaQueryWrapper
                .eq(Article::getUid, article.getUid())
                .eq(Article::getArticleId, article.getArticleId());
        if (articleMapper.update(article, articleLambdaQueryWrapper) != 0) return true;
        return false;
    }
}
