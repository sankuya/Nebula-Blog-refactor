package com.stu.nebulablog.controller;

import com.stu.nebulablog.module.ResponseData;
import com.stu.nebulablog.module.entity.Q;
import com.stu.nebulablog.service.faq.QuestionGetService;
import com.stu.nebulablog.service.faq.QuestionSearchService;
import com.stu.nebulablog.service.faq.QuestionListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/public/question")
public class PublicQuestionController {
    @Autowired
    private QuestionListService questionListService;
    @Autowired
    private QuestionSearchService questionSearchService;
    @Autowired
    private QuestionGetService questionGetService;
    private static final int MAXSIZE=10;
    @PostMapping("/list")
    public ResponseData list(@RequestBody Map<String ,String > src) {
        ResponseData responseData=new ResponseData();
        Integer page = Integer.valueOf(src.get("page"));
        Integer size=Integer.valueOf(src.get("size"));
        size=Math.min(MAXSIZE,size);
        Map<String ,Object>data= questionListService.doList(size, page);
        responseData.setCode(700);
        responseData.setData(data);
        return responseData;
    }
    @PostMapping("/search")
    public ResponseData getQASearch(@RequestBody Map<String ,String > src) {
        ResponseData responseData=new ResponseData();
        Integer page = Integer.valueOf(src.get("page"));
        String keyword =src.get("keyword");
        Integer size= Integer.valueOf(src.get("size"));
        size=Math.min(size,MAXSIZE);
        Map<String ,Object  >data=questionSearchService.doQuestionSearch(keyword,page,size);
        responseData.setCode(700);
        responseData.setData(data);
        return responseData;
    }
    @PostMapping("/get")
    public ResponseData getQData(@RequestBody Map<String ,String > src) {
        ResponseData responseData=new ResponseData();
        Integer qid =Integer.valueOf(src.get("qid"));
        Q q = questionGetService.doGetQuestion(qid);
        if (q == null){
            responseData.setCode(701);
        }else{
            responseData.setCode(700);
            responseData.setData(q);
        }
        return responseData;
    }
}
