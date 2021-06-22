package com.stu.nebulablog.service.article;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.stu.nebulablog.mapper.ArticleMapper;
import com.stu.nebulablog.mapper.UserInfoMapper;
import com.stu.nebulablog.module.entity.Article;
import com.stu.nebulablog.module.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticlePostService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;

    public boolean doPostArticle(Article article) {
        LambdaQueryWrapper<UserInfo> userInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userInfoLambdaQueryWrapper.eq(UserInfo::getUid, article.getUid())
                .select(UserInfo::getUsername);
        article.setAuthor(userInfoMapper.selectOne(userInfoLambdaQueryWrapper).getUsername());
        LambdaQueryWrapper<Article> articleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        articleLambdaQueryWrapper
                .eq(Article::getUid, article.getUid())
                .eq(Article::getTitle, article.getTitle());
        if (articleMapper.selectCount(articleLambdaQueryWrapper) == 0 && articleMapper.insert(article) != 0)
            return true;
        return false;
    }
}
