package com.stu.nebulablog.service.article;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.stu.nebulablog.mapper.ArticleMapper;
import com.stu.nebulablog.module.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleEditService {
    @Autowired
    private ArticleMapper articleMapper;
    public boolean doEditArticle(Article article){
        QueryWrapper<Article>articleQueryWrapper=new QueryWrapper<>();
        articleQueryWrapper.eq("uid",article.getUid());
        articleQueryWrapper.eq("art_id",article.getArt_id());
        if(articleMapper.update(article,articleQueryWrapper)!=0)return true;
        return false;
    }
}
