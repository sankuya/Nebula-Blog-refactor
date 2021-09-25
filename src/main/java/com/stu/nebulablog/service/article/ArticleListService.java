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

    public PageDataVO<Article> list(Integer uid,int page,  int size) {
        Page<Article> articlePage = new Page<>(page, size);
        LambdaQueryWrapper<Article> articleLambdaQueryWrapper = new QueryWrapper<Article>()
                .select("article_id", "author", "title", "date", "uid", "LEFT(content,"+SUMMARY_SIZE+") AS summary")
                .lambda()
                .eq(uid != null, Article::getUid, uid)
                .orderByDesc(Article::getArticleId);
        articleMapper.selectPage(articlePage, articleLambdaQueryWrapper);
        PageDataVO<Article> res = articlePageToMapUtil.getMapFromPageWithPages(articlePage);
        return res;
    }
}
