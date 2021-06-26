package com.stu.nebulablog.controller;

import com.stu.nebulablog.module.ResponseData;
import com.stu.nebulablog.module.entity.Question;
import com.stu.nebulablog.service.question.QuestionPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("question")
public class QuestionController {
    @Autowired
    private QuestionPostService questionPostService;
    @PostMapping("/post")
    public ResponseData questionPost(@RequestBody Question question, HttpSession session) {
        ResponseData responseData=new ResponseData();
        Integer uid = (Integer)session.getAttribute("uid");
        question.setUid(uid);
        question.setQuestionId(null);
        question.setAnswer(0);
        if (questionPostService.doPost(question)){
            responseData.setCode(800);
        }else{
            responseData.setCode(801);
        }
        return responseData;
    }
}
