package com.stu.nebulablog.service.article;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stu.nebulablog.mapper.ArticleMapper;
import com.stu.nebulablog.module.entity.Article;
import com.stu.nebulablog.module.vo.PageDataVO;
import com.stu.nebulablog.service.user.UserDetailGetService;
import com.stu.nebulablog.utils.PageToMapUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ArticleSearchService {
    @Autowired
    private PageToMapUtil<Article> articlePageToMapUtil;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private UserDetailGetService userDetailGetService;
    private static final int SUMMARY_SIZE = 255;
    @Autowired
    private ArticleSearchService articleSearchService;

    @Cacheable(cacheNames = "articleList")
    public PageDataVO<Article> doSearchArticle(String keyword, int page, int size) {
        Page<Article> articlePage = new Page<>(page, size);
        LambdaQueryWrapper<Article> articleLambdaQueryWrapper = new QueryWrapper<Article>()
                .select("article_id", "title", "date", "uid", "LEFT(content," + SUMMARY_SIZE + ") AS summary")
                .lambda()
                .like(Article::getTitle, keyword);
        articleMapper.selectPage(articlePage, articleLambdaQueryWrapper);
        return articlePageToMapUtil.getMapFromPageWithPages(articlePage);
    }

    public PageDataVO<Article> searchArticle(String keyword, int page, int size) {
        PageDataVO<Article> articlePageDataVO = articleSearchService.doSearchArticle(keyword, page, size);
        articlePageDataVO.getDetail().forEach(article ->
                article.setAuthor(userDetailGetService.getUserDetail(article.getUid()).getNickname()));
        return articlePageDataVO;
    }
}
