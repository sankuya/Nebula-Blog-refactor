package com.stu.nebulablog.controller;

import com.stu.nebulablog.module.ResponseData;
import com.stu.nebulablog.module.entity.Question;
import com.stu.nebulablog.service.question.QuestionGetService;
import com.stu.nebulablog.service.question.QuestionSearchService;
import com.stu.nebulablog.service.question.QuestionListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public ResponseData list(@RequestParam int page,@RequestParam int size) {
        ResponseData responseData=new ResponseData();
        size=Math.min(MAXSIZE,size);
        Map<String ,Object>data= questionListService.doList(size, page);
        responseData.setCode(700);
        responseData.setData(data);
        return responseData;
    }
    @PostMapping("/search")
    public ResponseData getQASearch(@RequestParam int page,@RequestParam int size,@RequestParam String keyword) {
        ResponseData responseData=new ResponseData();
        size=Math.min(size,MAXSIZE);
        Map<String ,Object  >data=questionSearchService.doQuestionSearch(keyword,page,size);
        responseData.setCode(700);
        responseData.setData(data);
        return responseData;
    }
    @PostMapping("/get")
    public ResponseData getQData(@RequestParam int qid) {
        ResponseData responseData=new ResponseData();
        Question question = questionGetService.doGetQuestion(qid);
        if (question == null){
            responseData.setCode(701);
        }else{
            responseData.setCode(700);
            responseData.setData(question);
        }
        return responseData;
    }
}
