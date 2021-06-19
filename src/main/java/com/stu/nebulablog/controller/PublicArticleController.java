package com.stu.nebulablog.controller;

import com.stu.nebulablog.module.ResponseData;
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
    private static final int MAXSIZE = 10;

    @PostMapping("/list")
    public ResponseData getArticleList(@RequestParam int page, @RequestParam int size) {
        ResponseData responseData = new ResponseData();
        size=Math.min(MAXSIZE,size);
        Map<String, Object> data = articleListService.listAll(page, size);
        responseData.setCode(200);
        responseData.setData(data);
        return responseData;
    }
    @PostMapping("/search")
    public Object search(@RequestParam int page,@RequestParam int size,@RequestParam String keyword) {
        ResponseData responseData = new ResponseData();
        size = Math.min(size, MAXSIZE);
        Map<String, Object> data = articleSearchService.doSearchArticle(keyword, page, size);
        responseData.setCode(400);
        responseData.setData(data);
        return responseData;
    }
}
