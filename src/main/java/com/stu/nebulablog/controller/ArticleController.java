package com.stu.nebulablog.controller;

import com.stu.nebulablog.module.ResponseData;
import com.stu.nebulablog.module.entity.Article;
import com.stu.nebulablog.service.article.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("article")
public class ArticleController {
    @Autowired
    private ArticleListService articleListService;
    @Autowired
    private ArticlePostService articlePostService;
    @Autowired
    private ArticleDeleteService articleDeleteService;
    @Autowired
    private ArticleEditService articleEditService;
    @Autowired
    private ArticleGetService articleGetService;
    private static final int MAXSIZE = 10;

    @PostMapping("/delete")
    public ResponseData delete(@RequestBody Map<String, String> src, HttpSession session) {
        ResponseData responseData = new ResponseData();
        Integer uid = (Integer) session.getAttribute("uid");
        Integer art_id = Integer.valueOf(src.get("id"));
        if (articleDeleteService.doDeleteArticle(uid, art_id)) {
            responseData.setCode(400);
        } else {
            responseData.setCode(401);
        }
        return responseData;
    }

    @PostMapping("/edit")
    public ResponseData edit(@RequestBody Article article, HttpSession session) {
        ResponseData responseData = new ResponseData();
        Integer uid = (Integer) session.getAttribute("uid");
        if (uid.equals(article.getUid()) && articleEditService.doEditArticle(article)) {
            responseData.setCode(400);
        } else {
            responseData.setCode(401);
        }
        return responseData;
    }

    @PostMapping("/get")
    public ResponseData get(@RequestBody Map<String, String> src) {
        ResponseData responseData = new ResponseData();
        Integer artId = Integer.valueOf(src.get("artId"));
        Article article = articleGetService.doGetArticle(artId);
        if (article != null) {
            responseData.setCode(400);
            responseData.setData(article);
        } else {
            responseData.setCode(401);
        }
        return responseData;
    }


    @PostMapping("/list")
    public ResponseData getArticleList(@RequestBody Map<String, String> src, HttpSession session) {
        ResponseData responseData = new ResponseData();
        Integer page = Integer.valueOf(src.get("page"));
        Integer uid = (Integer) session.getAttribute("uid");
        Integer size = Integer.valueOf(src.get("size"));
        if (size == null)
            size = Integer.MAX_VALUE;
        size = Math.min(size, MAXSIZE);
        Map<String, Object> data = articleListService.listByUid(page, uid, size);
        responseData.setCode(400);
        responseData.setData(data);
        return responseData;
    }

    @PostMapping("/post")
    public ResponseData postArticle(@RequestBody Article srcArticle, HttpSession session) {
        ResponseData responseData = new ResponseData();
        Integer uid = (Integer) session.getAttribute("uid");
        srcArticle.setUid(uid);
        if (articlePostService.doPostArticle(srcArticle)) {
            responseData.setCode(400);
        } else {
            responseData.setCode(401);
        }
        return responseData;
    }
}
