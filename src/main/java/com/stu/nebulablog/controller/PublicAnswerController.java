package com.stu.nebulablog.controller;

import com.stu.nebulablog.module.ResponseData;
import com.stu.nebulablog.module.entity.Answer;
import com.stu.nebulablog.module.vo.PageDataVO;
import com.stu.nebulablog.service.answer.AnswerListService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/public/answer")
@AllArgsConstructor
public class PublicAnswerController {
    private final AnswerListService answerListService;
    private static final int MAXSIZE = 10;

    @GetMapping("/list")
    public ResponseData list(@RequestParam @Nullable Integer questionId,
                             @RequestParam @Nullable Integer page, @RequestParam @Nullable Integer size) {
        ResponseData responseData = ResponseData.success();
        size = Optional.ofNullable(size).map(notNullSize -> Math.min(MAXSIZE, notNullSize)).orElse(MAXSIZE);
        PageDataVO<Answer> data = answerListService.list(questionId, page, size);
        responseData.setData(data);
        return responseData;
    }
}
