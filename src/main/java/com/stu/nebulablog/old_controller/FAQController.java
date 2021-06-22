package com.stu.nebulablog.old_controller;

import com.stu.nebulablog.mapper.UserInfoMapper;
import com.stu.nebulablog.module.entity.Answer;
import com.stu.nebulablog.module.entity.Question;
import com.stu.nebulablog.module.entity.UserInfo;
import com.stu.nebulablog.service.answer.AnswerGetService;
import com.stu.nebulablog.service.answer.AnswerPostService;
import com.stu.nebulablog.service.question.QuestionGetService;
import com.stu.nebulablog.service.question.QuestionListService;
import com.stu.nebulablog.service.question.QuestionPostService;
import com.stu.nebulablog.service.question.QuestionSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

//@RestController
@RequestMapping("FAQ")
public class FAQController {
    private final int size = 9;
    @Autowired
    private AnswerPostService answerPostService;
    @Autowired
    private AnswerGetService answerGetService;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private QuestionListService questionListService;
    @Autowired
    private QuestionSearchService questionSearchService;
    @Autowired
    private QuestionGetService questionGetService;
    @Autowired
    private QuestionPostService questionPostService;

    @PostMapping("/APost")
    public String aPost(@RequestBody Answer answer, HttpSession session) {
        Integer uid = (Integer) session.getAttribute("userid");
        UserInfo userInfo = userInfoMapper.selectById(uid);
        if (userInfo == null)
            return "wrong";
        String username = userInfo.getUsername();
        answer.setUsername(username);
        answer.setUid(uid);
        if (answerPostService.doAnswerPost(answer))
            return "ok";
        else
            return "wrong";
    }

    @PostMapping("/getAData")
    public List<Answer> ADataGet(@RequestParam int qid) {
        return answerGetService.doGetA(qid);
    }

    @PostMapping("/getQAList")
    public Map<String, Object> getQAList(@RequestBody Map<String, String> src) {
        Integer page = Integer.valueOf(src.get("page"));
        return questionListService.doList(size, page);
    }

    @PostMapping("/getQASearch")
    public Map<String, Object> getQASearch(@RequestBody Map<String, String> src) {
        Integer page = Integer.valueOf(src.get("page"));
        String keyword = src.get("keyword");
        return questionSearchService.doQuestionSearch(keyword, page, 9);
    }

    @PostMapping("/getQData")
    public Object getQData(@RequestBody Map<String, String> src) {
        Integer qid = Integer.valueOf(src.get("qid"));
        Question question = questionGetService.doGetQuestion(qid);
        if (question == null)
            return "没有这篇问题";
        else
            return question;
    }

    @PostMapping("/QPost")
    public String questionPost(@RequestBody Question question, HttpSession session) {
        Integer uid = (Integer) session.getAttribute("userid");
        question.setUid(uid);
        if (questionPostService.doPost(question))
            return "ok";
        return "wrong";
    }
}
