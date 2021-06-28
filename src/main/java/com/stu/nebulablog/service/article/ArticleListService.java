package com.stu.nebulablog.service.article;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stu.nebulablog.mapper.ArticleMapper;
import com.stu.nebulablog.module.entity.Article;
import com.stu.nebulablog.utils.PageToMapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Cacheable(cacheNames = "articleList")
public class ArticleListService {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private PageToMapUtil<Article>articlePageToMapUtil;

    public Map<String, Object> listAll(int p, int size) {
        Page<Article> articlePage = new Page<>(p, size);
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Article::getArticleId,Article::getAuthor,Article::getTitle,Article::getDate);
        articleMapper.selectPage(articlePage, queryWrapper);
        return articlePageToMapUtil.getMapFromPageWithPages(articlePage);
    }

    public Map<String, Object> listByUid(int p, int uid, int size) {
        Page<Article> articlePage = new Page<>(p, size);
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("uid", uid)
                .select("art_id", "title", "author", "date");
        articleMapper.selectPage(articlePage, queryWrapper);
        return articlePageToMapUtil.getMapFromPageWithPages(articlePage);
    }
}
