package com.stu.nebulablog.controller;

import com.stu.nebulablog.mapper.UserDetailMapper;
import com.stu.nebulablog.mapper.UserInfoMapper;
import com.stu.nebulablog.module.ResponseData;
import com.stu.nebulablog.module.entity.Answer;
import com.stu.nebulablog.module.entity.UserDetail;
import com.stu.nebulablog.module.entity.UserInfo;
import com.stu.nebulablog.service.answer.AnswerPostService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("answer")
@AllArgsConstructor
public class AnswerController {
    private final UserInfoMapper userInfoMapper;
    private final AnswerPostService answerPostService;
    private final UserDetailMapper userDetailMapper;

    @PostMapping("/post")
    public ResponseData aPost(@RequestBody Answer answer, @SessionAttribute Integer uid) {
        UserInfo userInfo = userInfoMapper.selectById(uid);
        UserDetail userDetail = userDetailMapper.selectById(uid);
        if (userInfo != null) {
            String username = userInfo.getUsername();
            answer.setUsername(username);
            answer.setUid(uid);
            answer.setAuthor(userDetail.getNickname());
            if (answerPostService.doPost(answer)) {
                return ResponseData.success();
            }
        }
        return ResponseData.fail();
    }
}
