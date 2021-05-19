package com.stu.nebulablog.service.article;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.stu.nebulablog.mapper.ArticleMapper;
import com.stu.nebulablog.module.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticlePostService {
    @Autowired
    private ArticleMapper articleMapper;

    public boolean doPostArticle(Article article) {
        QueryWrapper<Article> articleQueryWrapper = new QueryWrapper<>();
        articleQueryWrapper.eq("uid", article.getUid());
        articleQueryWrapper.eq("title", article.getTitle());
        if (articleMapper.selectOne(articleQueryWrapper) == null&&articleMapper.insert(article)!=0) return true;
        return false;
    }
}
