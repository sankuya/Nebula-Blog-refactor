package com.stu.nebulablog.service.article;

import com.stu.nebulablog.mapper.ArticleMapper;
import com.stu.nebulablog.module.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleGetService {
    @Autowired
    private ArticleMapper articleMapper;
    public Article doGetArticle(Integer artId){
       return articleMapper.selectById(artId);
    }
}
