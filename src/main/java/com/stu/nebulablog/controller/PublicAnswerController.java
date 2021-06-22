package com.stu.nebulablog.controller;

import com.stu.nebulablog.module.ResponseData;
import com.stu.nebulablog.module.entity.Answer;
import com.stu.nebulablog.service.answer.AnswerGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public/answer")
public class PublicAnswerController {
   @Autowired
   private AnswerGetService answerGetService;
    @GetMapping("/getByQuestionId")
    public ResponseData getByQuestionId(@RequestParam int questionId) {
        ResponseData responseData=new ResponseData();
        List<Answer>data=answerGetService.doGet(questionId);
        responseData.setCode(600);
        responseData.setData(data);
        return responseData;
    }
}