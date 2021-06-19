package com.stu.nebulablog.service.article;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stu.nebulablog.mapper.ArticleMapper;
import com.stu.nebulablog.module.entity.Article;
import com.stu.nebulablog.utils.PageToMapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ArticleSearchService {
    @Autowired
    PageToMapUtil<Article> articlePageToMapUtil;
    @Autowired
    private ArticleMapper articleMapper;

    public Map<String, Object> doSearchArticle(String keyword, int page, int size) {
        Page<Article> articlePage = new Page<>(page, size);
        QueryWrapper<Article> articleQueryWrapper = new QueryWrapper<>();
        articleQueryWrapper
                .like("title", keyword)
                .or()
                .like("author", keyword);
        articleMapper.selectPage(articlePage, articleQueryWrapper);
        return articlePageToMapUtil.getMapFromPageWithPages(articlePage);
    }
}
