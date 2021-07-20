package com.stu.nebulablog.controller;

import com.stu.nebulablog.module.ResponseData;
import com.stu.nebulablog.module.entity.Article;
import com.stu.nebulablog.module.vo.PageDataVO;
import com.stu.nebulablog.service.article.ArticleGetService;
import com.stu.nebulablog.service.article.ArticleListService;
import com.stu.nebulablog.service.article.ArticleSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
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
    public ResponseData getArticleList(@RequestParam int page, @RequestParam int size, @Nullable @RequestParam Integer uid) {
        ResponseData responseData = ResponseData.success();
        size = Math.min(MAXSIZE, size);
        PageDataVO data = articleListService.list(page, uid, size);
        responseData.setData(data);
        return responseData;
    }

    @GetMapping("/search")
    public Object search(@RequestParam int page, @RequestParam int size, @RequestParam String keyword) {
        ResponseData responseData = ResponseData.success();
        size = Math.min(size, MAXSIZE);
        PageDataVO data = articleSearchService.doSearchArticle(keyword, page, size);
        responseData.setData(data);
        return responseData;
    }

    @GetMapping("/get")
    public ResponseData get(@RequestParam int articleId) {
        Article article = articleGetService.doGetArticle(articleId);
        if (article != null) {
            ResponseData responseData = ResponseData.success();
            responseData.setData(article);
            return responseData;
        } else {
            return ResponseData.fail();
        }
    }
}
