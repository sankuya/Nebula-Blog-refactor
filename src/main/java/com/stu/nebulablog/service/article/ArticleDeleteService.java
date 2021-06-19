package com.stu.nebulablog.service.article;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.stu.nebulablog.mapper.ArticleMapper;
import com.stu.nebulablog.module.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleDeleteService {
    @Autowired
    private ArticleMapper articleMapper;
    public boolean doDeleteArticle(Integer uid,Integer art_id){
        QueryWrapper<Article>articleQueryWrapper=new QueryWrapper<>();
        articleQueryWrapper.eq("uid",uid);
        articleQueryWrapper.eq("art_id",art_id);
        if(articleMapper.delete(articleQueryWrapper)!=0)return true;
        return false;
    }
}
