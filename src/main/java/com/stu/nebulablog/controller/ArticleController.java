package com.stu.nebulablog.controller;

import com.stu.nebulablog.module.ResponseData;
import com.stu.nebulablog.module.entity.Article;
import com.stu.nebulablog.module.vo.PageDataVO;
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
    private static final int MAXSIZE = 10;

    @PostMapping("/delete")
    public ResponseData delete(@RequestParam int articleId, HttpSession session) {
        Integer uid = (Integer) session.getAttribute("uid");
        if (uid == null) {
            return ResponseData.fail();
        } else if (articleDeleteService.doDeleteArticle(uid, articleId)) {
            return ResponseData.success();
        } else {
            return ResponseData.fail();
        }
    }

    @PostMapping("/edit")
    public ResponseData edit(@RequestBody Article article, HttpSession session) {
        Integer uid = (Integer) session.getAttribute("uid");
        if (uid.equals(article.getUid()) && articleEditService.doEditArticle(article)) {
            return ResponseData.success();
        } else {
            return ResponseData.fail();
        }
    }


    @GetMapping("/list")
    public ResponseData getArticleList(@RequestParam int page, @RequestParam int size, HttpSession session) {
        ResponseData responseData = new ResponseData();
        Integer uid = (Integer) session.getAttribute("uid");
        size = Math.min(size, MAXSIZE);
        PageDataVO<Article> data = articleListService.list(page, uid, size);
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
