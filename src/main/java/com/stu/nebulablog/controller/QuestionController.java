package com.stu.nebulablog.controller;

import com.stu.nebulablog.module.ResponseData;
import com.stu.nebulablog.module.entity.Q;
import com.stu.nebulablog.service.faq.QuestionPostService;
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
    public ResponseData questionPost(@RequestBody Q q, HttpSession session) {
        ResponseData responseData=new ResponseData();
        Integer uid = (Integer)session.getAttribute("uid");
        q.setUid(uid);
        if (questionPostService.doPost(q)){
            responseData.setCode(800);
        }else{
            responseData.setCode(801);
        }
        return responseData;
    }
}
