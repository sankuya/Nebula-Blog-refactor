package com.stu.nebulablog.service.article;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stu.nebulablog.mapper.ArticleMapper;
import com.stu.nebulablog.module.entity.Article;
import com.stu.nebulablog.module.vo.PageDataVO;
import com.stu.nebulablog.utils.PageToMapUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class ArticleSearchService {
    private final PageToMapUtil<Article> articlePageToMapUtil;
    private final ArticleMapper articleMapper;

    @Cacheable("articleList")
    public PageDataVO<Article> doSearchArticle(String keyword, int page, int size) {
        Page<Article> articlePage = new Page<>(page, size);
        LambdaQueryWrapper<Article> articleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        articleLambdaQueryWrapper
                .like(Article::getTitle, keyword)
                .or()
                .like(Article::getAuthor, keyword);
        articleMapper.selectPage(articlePage, articleLambdaQueryWrapper);
        return articlePageToMapUtil.getMapFromPageWithPages(articlePage);
    }
}
