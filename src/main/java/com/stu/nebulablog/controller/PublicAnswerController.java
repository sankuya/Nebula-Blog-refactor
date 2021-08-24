package com.stu.nebulablog.controller;

import com.stu.nebulablog.module.ResponseData;
import com.stu.nebulablog.module.entity.Answer;
import com.stu.nebulablog.service.answer.AnswerListService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public/answer")
@AllArgsConstructor
public class PublicAnswerController {
    private final AnswerListService answerListService;

    @GetMapping("/getByQuestionId")
    public ResponseData getByQuestionId(@RequestParam int questionId) {
        ResponseData responseData = ResponseData.success();
        List<Answer> data = answerListService.doList(questionId);
        responseData.setData(data);
        return responseData;
    }
}
