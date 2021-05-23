package com.stu.nebulablog.controller;

import com.stu.nebulablog.module.Article;
import com.stu.nebulablog.service.article.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleDeleteService articleDeleteService;
    @Autowired
    private ArticleEditService articleEditService;
    @Autowired
    private ArticleDataGetService articleDataGetService;
    @Autowired
    private ArticleListGetService articleListGetService;
    @Autowired
    private ArticleSearchService articleSearchService;
    @Autowired
    private ArticlePostService articlePostService;
    private final int size = 12;

    @PostMapping("/deleteArticle")
    public Object deleteArticle(@RequestBody Map<String,String> src, HttpSession session) {
        Integer uid = (Integer) session.getAttribute("userid");
        Integer art_id =Integer.valueOf( src.get("id"));
        if (articleDeleteService.doDeleteArticle(uid, art_id)) return "ok";
        return "wrong";
    }

    @PostMapping("/editArticle")
    public Object editArticle(@RequestBody Article article, HttpSession session) {
        Integer uid = (Integer) session.getAttribute("userid");
        if (uid != article.getUid()) return "wrong";
        if (articleEditService.doEditArticle(article)) return "ok";
        return "wrong";
    }

    @PostMapping("/getArticleData")
    public Object getArticleData(@RequestBody Map<String ,String> src) {
        Integer art_id =Integer.valueOf(src.get("artid"));
        Article article = articleDataGetService.doGetArticleData(art_id);
        if (article != null) return article;
        return "没有这篇文章";
    }

    @PostMapping("/getArticleList")
    public Object getArticleList(@RequestBody Map<String ,String> src, HttpSession session) {
        String type = src.get("type");
        Integer page =Integer.valueOf(src.get("page")) ;
        if (type == null) return null;
        Map<String, Object> res;
        if (type.equals("all")) {
            res = articleListGetService.getAllArticleList(page, size);
        } else {
            Integer uid = (Integer) session.getAttribute("userid");
            if (uid == null) return null;
            res = articleListGetService.getArticleListByUserId(page, uid, size);
        }
        return res;
    }

    @PostMapping("/getSearchResult")
    public Object getSearchResult(@RequestBody Map<String ,String > src) {
        Integer p =Integer.valueOf(src.get("page"));
        String keyword =src.get("keyword");
        Map<String, Object> res = articleSearchService.doSearchArticle(keyword, p, size);
        return res;
    }

    @PostMapping("/postArticle")
    public Object postArticle(@RequestBody Article srcArticle, HttpSession session) {
        Integer uid = (Integer) session.getAttribute("userid");
        if (uid == null) return "wrong";
        srcArticle.setUid(uid);
        if (articlePostService.doPostArticle(srcArticle)) return "ok";
        else return "wrong";
    }
}
