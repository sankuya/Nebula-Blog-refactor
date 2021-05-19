package com.stu.nebulablog.service.article;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stu.nebulablog.mapper.ArticleMapper;
import com.stu.nebulablog.module.Article;
import com.stu.nebulablog.utils.PageToMapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ArticleListGetService {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private PageToMapUtil<Article>articlePageToMapUtil;
    public Map<String, Object> getAllArticleList(int p, int size) {
        Page<Article> articlePage = new Page<>(p, size);
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("art_id", "title", "author", "date");
        articleMapper.selectPage(articlePage, queryWrapper);
        return articlePageToMapUtil.getMapFromPageWithPages(articlePage);
    }

    public Map<String, Object> getArticleListByUserId(int p, int userId, int size) {
        Page<Article> articlePage = new Page<>(p, size);
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("uid", userId)
                .select("art_id", "title", "author", "date");
        articleMapper.selectPage(articlePage, queryWrapper);
        return articlePageToMapUtil.getMapFromPageWithPages(articlePage);
    }
}
