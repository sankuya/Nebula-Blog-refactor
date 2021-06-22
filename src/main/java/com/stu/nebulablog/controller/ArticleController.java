package com.stu.nebulablog.controller;

import com.stu.nebulablog.module.ResponseData;
import com.stu.nebulablog.module.entity.Article;
import com.stu.nebulablog.service.article.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public ResponseData delete(@RequestParam int articleId, HttpSession session) {
        ResponseData responseData = new ResponseData();
        Integer uid = (Integer) session.getAttribute("uid");
        if (uid == null) {
            responseData.setCode(401);
            responseData.setMsg("未登录不能删除文章");
        } else if (articleDeleteService.doDeleteArticle(uid, articleId)) {
            responseData.setCode(400);
        } else {
            responseData.setCode(402);
            responseData.setMsg("不能删除别人的文章");
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


    @GetMapping ("/list")
    public ResponseData getArticleList(@RequestParam int page, @RequestParam int size, HttpSession session) {
        ResponseData responseData = new ResponseData();
        Integer uid = (Integer) session.getAttribute("uid");
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
