package com.stu.nebulablog.service.article;

import com.stu.nebulablog.mapper.ArticleMapper;
import com.stu.nebulablog.module.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleDataGetService {
    @Autowired
    private ArticleMapper articleMapper;
    public Article doGetArticleData(Integer art_id){
       return articleMapper.selectById(art_id);
    }
}
