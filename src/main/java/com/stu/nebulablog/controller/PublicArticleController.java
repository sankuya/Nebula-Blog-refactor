package com.stu.nebulablog.controller;

import com.stu.nebulablog.module.ResponseData;
import com.stu.nebulablog.module.entity.Article;
import com.stu.nebulablog.module.vo.PageDataVO;
import com.stu.nebulablog.service.article.ArticleGetService;
import com.stu.nebulablog.service.article.ArticleListService;
import com.stu.nebulablog.service.article.ArticleSearchService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/public/article")
@AllArgsConstructor
public class PublicArticleController {
    private final ArticleListService articleListService;
    private final ArticleSearchService articleSearchService;
    private final ArticleGetService articleGetService;
    private static final int MAXSIZE = 10;

    @GetMapping("list")
    public ResponseData list(@RequestParam int page, @RequestParam int size, @Nullable @RequestParam Integer uid) {
        ResponseData responseData = ResponseData.success();
        size = Math.min(MAXSIZE, size);
        PageDataVO<Article> data = articleListService.listArticle(uid, page, size);
        responseData.setData(data);
        return responseData;
    }

    @GetMapping("search")
    public Object search(@RequestParam int page, @RequestParam int size, @RequestParam String keyword) {
        ResponseData responseData = ResponseData.success();
        size = Math.min(size, MAXSIZE);
        PageDataVO<Article> data = articleSearchService.searchArticle(keyword, page, size);
        responseData.setData(data);
        return responseData;
    }

    @GetMapping("get")
    public ResponseData get(@RequestParam int articleId) {
        Article article = articleGetService.getArticleDetail(articleId);
        if (article != null) {
            ResponseData responseData = ResponseData.success();
            responseData.setData(article);
            return responseData;
        } else {
            return ResponseData.fail();
        }
    }
}
