package com.stu.nebulablog.controller;

import com.stu.nebulablog.module.ResponseData;
import com.stu.nebulablog.module.entity.Answer;
import com.stu.nebulablog.module.vo.PageDataVO;
import com.stu.nebulablog.service.answer.AnswerGetService;
import com.stu.nebulablog.service.answer.AnswerListService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/public/answer")
@AllArgsConstructor
public class PublicAnswerController {
    private final AnswerListService answerListService;
    private final AnswerGetService answerGetService;
    private final int maxSize;

    @GetMapping("list")
    public ResponseData list(@RequestParam @Nullable Integer questionId,
                             @RequestParam @Nullable Integer page, @RequestParam @Nullable Integer size) {
        ResponseData responseData = ResponseData.success();
        size = Optional.ofNullable(size).map(notNullSize -> Math.min(maxSize, notNullSize)).orElse(maxSize);
        PageDataVO<Answer> data = answerListService.listAnswer(questionId, page, size);
        responseData.setData(data);
        return responseData;
    }

    @GetMapping("get")
    public ResponseData get(@RequestParam Long answerId) {
        ResponseData responseData = ResponseData.success();
        responseData.setData(answerGetService.getAnswer(answerId));
        return responseData;
    }
}
