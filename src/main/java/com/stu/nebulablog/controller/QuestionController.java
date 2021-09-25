package com.stu.nebulablog.controller;

import com.stu.nebulablog.module.ResponseData;
import com.stu.nebulablog.module.entity.Question;
import com.stu.nebulablog.service.question.QuestionPostService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("question")
@AllArgsConstructor
public class QuestionController {
    private final QuestionPostService questionPostService;

    @PostMapping("/post")
    public ResponseData questionPost(@RequestBody Question question, @SessionAttribute Integer uid) {
        question.setUid(uid);
        question.setQuestionId(null);
        question.setAnswerNum(0);
        if (questionPostService.doPost(question)) {
            return ResponseData.success();
        } else {
            return ResponseData.fail();
        }
    }
}
