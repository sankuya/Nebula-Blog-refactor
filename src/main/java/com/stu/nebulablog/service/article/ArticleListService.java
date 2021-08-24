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

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Cacheable(cacheNames = "articleList")
@AllArgsConstructor
public class ArticleListService {
    private final ArticleMapper articleMapper;
    private final PageToMapUtil<Article> articlePageToMapUtil;
    private static final int SUMMARY_SIZE = 255;

    public PageDataVO<Article> list(int p, Integer uid, int size) {
        Page<Article> articlePage = new Page<>(p, size);
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(uid != null, Article::getUid, uid)
                .select(Article::getArticleId, Article::getAuthor, Article::getTitle, Article::getDate, Article::getUid, Article::getContent)
                .orderByDesc(Article::getArticleId);
        articleMapper.selectPage(articlePage, queryWrapper);
        PageDataVO<Article> res = articlePageToMapUtil.getMapFromPageWithPages(articlePage);
        res.getDetail().forEach(article -> {//取内容的前n位作为摘要，todo：数据库中直接设置摘要字段
            String content = article.getContent();
            if (content == null) return;
            article.setSummary(content.substring(0, Math.min(SUMMARY_SIZE, article.getContent().length())));
            article.setContent(null);
        });
        return res;
    }
}
