package com.stu.nebulablog.controller;

import com.stu.nebulablog.module.ResponseData;
import com.stu.nebulablog.module.entity.Question;
import com.stu.nebulablog.module.vo.PageDataVO;
import com.stu.nebulablog.service.question.QuestionGetService;
import com.stu.nebulablog.service.question.QuestionSearchService;
import com.stu.nebulablog.service.question.QuestionListService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/public/question")
@AllArgsConstructor
public class PublicQuestionController {
    private final QuestionListService questionListService;
    private final QuestionSearchService questionSearchService;
    private final QuestionGetService questionGetService;
    private final int maxSize;

    @GetMapping("list")
    public ResponseData list(@RequestParam int page, @RequestParam int size) {
        size = Math.min(maxSize, size);
        PageDataVO<Question> data = questionListService.listQuestion(null, size, page);
        ResponseData responseData = ResponseData.success();
        responseData.setData(data);
        return responseData;
    }

    @GetMapping("search")
    public ResponseData search(@RequestParam int page, @RequestParam int size, @RequestParam String keyword) {
        size = Math.min(size, maxSize);
        PageDataVO<Question> data = questionSearchService.searchQuestion(keyword, page, size);
        ResponseData responseData = ResponseData.success();
        responseData.setData(data);
        return responseData;
    }

    @GetMapping("get")
    public ResponseData get(@RequestParam int questionId) {
        Question question = questionGetService.getQuestion(questionId);
        if (question == null) {
            return ResponseData.fail();
        } else {
            ResponseData responseData = ResponseData.success();
            responseData.setData(question);
            return responseData;
        }
    }
}
