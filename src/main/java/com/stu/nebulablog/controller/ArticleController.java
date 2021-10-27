package com.stu.nebulablog.controller;

import com.stu.nebulablog.module.ResponseData;
import com.stu.nebulablog.module.entity.Article;
import com.stu.nebulablog.module.vo.PageDataVO;
import com.stu.nebulablog.service.article.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("article")
@AllArgsConstructor
public class ArticleController {
    private final ArticleListService articleListService;
    private final ArticlePostService articlePostService;
    private final ArticleDeleteService articleDeleteService;
    private final ArticleEditService articleEditService;
    private static final int MAXSIZE = 10;

    @PostMapping("delete")
    public ResponseData delete(@RequestBody Map<String, Integer> src, @SessionAttribute Integer uid) {
        if (src.containsKey("articleId") && articleDeleteService.doDeleteArticle(uid, src.get("articleId")))
            return ResponseData.success();
        else return ResponseData.fail();
    }

    @PostMapping("edit")
    public ResponseData edit(@RequestBody Article article, @SessionAttribute Integer uid) {
        article.setUid(uid);
        if (articleEditService.doEditArticle(article))
            return ResponseData.success();
        else return ResponseData.fail();
    }


    @GetMapping("list")
    public ResponseData getArticleList(@RequestParam int page, @RequestParam int size, @SessionAttribute Integer uid) {
        ResponseData responseData = ResponseData.success();
        size = Math.min(size, MAXSIZE);
        PageDataVO<Article> data = articleListService.listArticle(uid, page, size);
        responseData.setData(data);
        return responseData;
    }

    @PostMapping("post")
    public ResponseData postArticle(@RequestBody Article srcArticle, @SessionAttribute Integer uid) {
        srcArticle.setUid(uid);
        if (articlePostService.doPostArticle(srcArticle))
            return ResponseData.success();
        else return ResponseData.fail();
    }
}
