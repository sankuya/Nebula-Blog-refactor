package com.stu.nebulablog.controller;

import com.stu.nebulablog.module.ResponseData;
import com.stu.nebulablog.service.article.ArticleListGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/public/article")
public class PublicArticleController {

    @Autowired
    private ArticleListGetService articleListGetService;
    private static final int MAXSIZE = 10;

    @PostMapping("/list")
    public ResponseData getArticleList(@RequestBody Map<String, String> src) {
        ResponseData responseData = new ResponseData();
        Integer page = Integer.valueOf(src.get("page"));
        Integer size=Integer.valueOf(src.get("size"));
        if(size==null)size=Integer.MAX_VALUE;
        size=Math.min(size,MAXSIZE);
        Map<String, Object> data = articleListGetService.getAllArticleList(page, size);
        responseData.setCode(200);
        responseData.setData(data);
        return responseData;
    }
}
