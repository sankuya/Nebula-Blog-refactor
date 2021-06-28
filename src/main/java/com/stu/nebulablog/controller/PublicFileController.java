package com.stu.nebulablog.controller;

import com.stu.nebulablog.module.ResponseData;
import com.stu.nebulablog.service.file.SharedFileInfoListService;
import com.stu.nebulablog.service.file.SharedFileInfoSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("public/file")
public class PublicFileController {
    @Autowired
    private SharedFileInfoListService sharedFileInfoListService;
    @Autowired
    private SharedFileInfoSearchService sharedFileInfoSearchService;
    private static final int MAXSIZE=10;
    @GetMapping("list")
    public ResponseData list(@RequestParam int page, @RequestParam int size) {
        ResponseData responseData = new ResponseData();
        size=Math.min(MAXSIZE,size);
        responseData.setData(sharedFileInfoListService.listSharedFileInfo(page, size));
        if (((List)((Map)responseData.getData()).get("detail")).size()!= 0) {
            responseData.setCode(900);
        } else {
            responseData.setCode(901);
            responseData.setMsg("获取失败");
        }
        return responseData;
    }
    @GetMapping("search")
    public ResponseData search(@RequestParam int page, @RequestParam int size,@RequestParam String keyword) {
        ResponseData responseData = new ResponseData();
        size=Math.min(MAXSIZE,size);
        responseData.setData(sharedFileInfoSearchService.searchSharedFileInfo(page,size,keyword));
        if (((List)((Map)responseData.getData()).get("detail")).size()!= 0) {
            responseData.setCode(900);
        } else {
            responseData.setCode(901);
            responseData.setMsg("获取失败");
        }
        return responseData;
    }
}
