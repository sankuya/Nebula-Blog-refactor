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
    private static final int MAXSIZE = 10;

    @GetMapping("/list")
    public ResponseData list(@RequestParam int page, @RequestParam int size) {
        size = Math.min(MAXSIZE, size);
        Map<String, Object> data = questionListService.doList(size, page);
        ResponseData responseData=ResponseData.success();
        responseData.setData(data);
        return responseData;
    }

    @GetMapping("/search")
    public ResponseData search(@RequestParam int page, @RequestParam int size, @RequestParam String keyword) {
        size = Math.min(size, MAXSIZE);
        Map<String, Object> data = questionSearchService.doQuestionSearch(keyword, page, size);
        ResponseData responseData=ResponseData.success();
        responseData.setData(data);
        return responseData;
    }

    @GetMapping("/get")
    public ResponseData get(@RequestParam int questionId) {
        Question question = questionGetService.doGetQuestion(questionId);
        if (question == null) {
            return  ResponseData.fail();
        } else {
            ResponseData responseData=ResponseData.success();
            responseData.setData(question);
            return ResponseData.success();
        }
    }
}
