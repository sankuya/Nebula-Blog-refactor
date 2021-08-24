package com.stu.nebulablog.service.article;

import com.stu.nebulablog.mapper.ArticleMapper;
import com.stu.nebulablog.module.entity.Article;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ArticleGetService {
    private final ArticleMapper articleMapper;

    @Cacheable(value = "article", key = "#artId")
    public Article doGetArticle(int artId) {
        return articleMapper.selectById(artId);
    }
}
