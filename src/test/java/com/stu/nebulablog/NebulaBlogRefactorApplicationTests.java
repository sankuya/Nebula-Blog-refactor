package com.stu.nebulablog;

import com.stu.nebulablog.mapper.*;
import com.stu.nebulablog.module.Q;
import com.stu.nebulablog.module.UserInfo;
import com.stu.nebulablog.service.article.ArticleEditService;
import com.stu.nebulablog.service.article.ArticleListGetService;
import com.stu.nebulablog.service.faq.AnswerGetService;
import com.stu.nebulablog.service.faq.QASearchGetService;
import com.stu.nebulablog.service.faq.QuestionDataGetService;
import com.stu.nebulablog.service.faq.QuestionPostService;
import com.stu.nebulablog.service.info.InfoChangeService;
import com.stu.nebulablog.service.login.RegistService;
import com.stu.nebulablog.utils.PasswordUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class NebulaBlogRefactorApplicationTests {
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    UserDetailMapper userDetailMapper;
    @Autowired
    QMapper qMapper;
    @Autowired
    AMapper aMapper;
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    RegistService registService;
    @Autowired
    ArticleListGetService articleListGetService;
    @Autowired
    InfoChangeService infoChangeService;
    @Autowired
    PasswordUtil passwordUtil;
    @Autowired
    ArticleEditService articleEditService;
    @Autowired
    AnswerGetService answerGetService;
    @Autowired
    QASearchGetService qaSearchGetService;
    @Autowired
    QuestionPostService questionPostService;
    @Autowired
    QuestionDataGetService questionDataGetService;
    @Value("${user.photo.preUrl}")
   private String s;
    @Value("${user.photo.preUrl}")
    private String preUrl;
    @Test
    void contextLoads() throws Exception {
        System.out.println(registService.doRegist(new UserInfo()));
    }

}
