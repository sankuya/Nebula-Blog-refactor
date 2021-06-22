package com.stu.nebulablog.controller;

import com.stu.nebulablog.module.ResponseData;
import com.stu.nebulablog.module.entity.Article;
import com.stu.nebulablog.service.article.ArticleGetService;
import com.stu.nebulablog.service.article.ArticleListService;
import com.stu.nebulablog.service.article.ArticleSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/public/article")
public class PublicArticleController {

    @Autowired
    private ArticleListService articleListService;
    @Autowired
    private ArticleSearchService articleSearchService;
    @Autowired
    private ArticleGetService articleGetService;
    private static final int MAXSIZE = 10;

    @GetMapping("/list")
    public ResponseData getArticleList(@RequestParam int page, @RequestParam int size) {
        ResponseData responseData = new ResponseData();
        size=Math.min(MAXSIZE,size);
        Map<String, Object> data = articleListService.listAll(page, size);
        responseData.setCode(200);
        responseData.setData(data);
        return responseData;
    }
    @GetMapping("/search")
    public Object search(@RequestParam int page,@RequestParam int size,@RequestParam String keyword) {
        ResponseData responseData = new ResponseData();
        size = Math.min(size, MAXSIZE);
        Map<String, Object> data = articleSearchService.doSearchArticle(keyword, page, size);
        responseData.setCode(400);
        responseData.setData(data);
        return responseData;
    }
    @GetMapping("/get")
    public ResponseData get(@RequestParam int articleId) {
        ResponseData responseData = new ResponseData();
        Article article = articleGetService.doGetArticle(articleId);
        if (article != null) {
            responseData.setCode(400);
            responseData.setData(article);
        } else {
            responseData.setCode(401);
        }
        return responseData;
    }
}