package com.stu.nebulablog.controller;

import com.stu.nebulablog.mapper.UserInfoMapper;
import com.stu.nebulablog.module.ResponseData;
import com.stu.nebulablog.module.entity.A;
import com.stu.nebulablog.module.entity.UserInfo;
import com.stu.nebulablog.service.faq.AnswerPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("answer")
public class AnswerController {
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private AnswerPostService answerPostService;
    @PostMapping("/post")
    public ResponseData aPost(@RequestBody A a, HttpSession session) {
        ResponseData responseData=new ResponseData();
        Integer uid = (Integer)session.getAttribute("uid");
        UserInfo userInfo = userInfoMapper.selectById(uid);
        if (userInfo != null){
            String username = userInfo.getUsername();
            a.setUsername(username);
            a.setUid(uid);
            if(answerPostService.doAnswerPost(a)){
                responseData.setCode(500);
                return responseData;
            }
        }
        responseData.setCode(501);
        return responseData;
    }
}
