package com.stu.nebulablog.controller;

import com.stu.nebulablog.module.ResponseData;
import com.stu.nebulablog.module.vo.PageDataVO;
import com.stu.nebulablog.service.file.SharedFileInfoListService;
import com.stu.nebulablog.service.file.SharedFileInfoSearchService;
import lombok.extern.slf4j.Slf4j;
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
    private static final int MAXSIZE = 10;

    @GetMapping("list")
    public ResponseData list(@RequestParam int page, @RequestParam int size) {
        ResponseData responseData = ResponseData.success();
        size = Math.min(MAXSIZE, size);
        PageDataVO<String> data = sharedFileInfoListService.listSharedFileInfo(page, size);
        if (data.getDetail().size() == 0) return ResponseData.fail();
        responseData.setData(data);
        return responseData;
    }

    @GetMapping("search")
    public ResponseData search(@RequestParam int page, @RequestParam int size, @RequestParam String keyword) {
        ResponseData responseData = new ResponseData();
        size = Math.min(MAXSIZE, size);
        PageDataVO<String> data = sharedFileInfoSearchService.searchSharedFileInfo(page, size, keyword);
        if (data.getDetail().size() == 0) return ResponseData.fail();
        responseData.setData(data);
        return responseData;
    }
}
