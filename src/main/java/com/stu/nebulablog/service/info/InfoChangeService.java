package com.stu.nebulablog.service.info;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.stu.nebulablog.mapper.AMapper;
import com.stu.nebulablog.mapper.ArticleMapper;
import com.stu.nebulablog.mapper.QMapper;
import com.stu.nebulablog.mapper.UserDetailMapper;
import com.stu.nebulablog.module.A;
import com.stu.nebulablog.module.Article;
import com.stu.nebulablog.module.Q;
import com.stu.nebulablog.module.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InfoChangeService {
    @Autowired
    private UserDetailMapper userDetailMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private QMapper qMapper;
    @Autowired
    private AMapper aMapper;

    @Transactional
    public boolean doInfoChange(UserDetail userDetail){
        Article article = new Article();
        Q q = new Q();
        A a = new A();
        article.setAuthor(userDetail.getNickname());
        article.setUid(userDetail.getUid());
        q.setAuthor(userDetail.getNickname());
        q.setUid(userDetail.getUid());
        a.setAuthor(userDetail.getNickname());
        a.setUid(userDetail.getUid());
        if (userDetailMapper.updateById(userDetail) == 0) return false;
        UpdateWrapper<Article> userDetailUpdateWrapper = new UpdateWrapper<>();
        userDetailUpdateWrapper.eq("uid", article.getUid());
        articleMapper.update(article, userDetailUpdateWrapper);
        UpdateWrapper<Q> qUpdateWrapper = new UpdateWrapper<>();
        qUpdateWrapper.eq("uid", q.getUid());
        qMapper.update(q, qUpdateWrapper);
        UpdateWrapper<A> aUpdateWrapper = new UpdateWrapper<>();
        aUpdateWrapper.eq("uid", a.getUid());
        aMapper.update(a, aUpdateWrapper);
        return true;
    }
}
