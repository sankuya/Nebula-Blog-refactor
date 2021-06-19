package com.stu.nebulablog.old_controller;

import com.stu.nebulablog.mapper.UserInfoMapper;
import com.stu.nebulablog.module.entity.A;
import com.stu.nebulablog.module.entity.Q;
import com.stu.nebulablog.module.entity.UserInfo;
import com.stu.nebulablog.service.faq.*;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public String aPost(@RequestBody A a, HttpSession session) {
        Integer uid = (Integer) session.getAttribute("userid");
        UserInfo userInfo = userInfoMapper.selectById(uid);
        if (userInfo == null)
            return "wrong";
        String username = userInfo.getUsername();
        a.setUsername(username);
        a.setUid(uid);
        if (answerPostService.doAnswerPost(a))
            return "ok";
        else
            return "wrong";
    }

    @PostMapping("/getAData")
    public List<A> ADataGet(@RequestBody JSONObject src) {
        Integer qid = Integer.valueOf(src.getString("qid"));
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
        Q q = questionGetService.doGetQuestion(qid);
        if (q == null)
            return "没有这篇问题";
        else
            return q;
    }

    @PostMapping("/QPost")
    public String questionPost(@RequestBody Q q, HttpSession session) {
        Integer uid = (Integer) session.getAttribute("userid");
        q.setUid(uid);
        if (questionPostService.doPost(q))
            return "ok";
        return "wrong";
    }
}
