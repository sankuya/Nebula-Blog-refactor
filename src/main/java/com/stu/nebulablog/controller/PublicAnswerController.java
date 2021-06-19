package com.stu.nebulablog.controller;

import com.stu.nebulablog.mapper.UserInfoMapper;
import com.stu.nebulablog.module.ResponseData;
import com.stu.nebulablog.module.entity.A;
import com.stu.nebulablog.module.entity.UserInfo;
import com.stu.nebulablog.service.faq.AnswerGetService;
import com.stu.nebulablog.service.faq.AnswerPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/public/answer")
public class PublicAnswerController {
   @Autowired
   private AnswerGetService answerGetService;
    @PostMapping("/getByQuestionId")
    public ResponseData getByQuestionId(@RequestBody Map<String ,String> src) {
        ResponseData responseData=new ResponseData();
        Integer qid =Integer.valueOf(src.get("qid"));
        List<A>data=answerGetService.doGetA(qid);
        responseData.setCode(600);
        responseData.setData(data);
        return responseData;
    }
}
