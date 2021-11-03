package com.stu.nebulablog.controller;

import com.stu.nebulablog.module.ResponseData;
import com.stu.nebulablog.module.vo.PageDataVO;
import com.stu.nebulablog.service.file.share.ShareFileGetService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("public/file")
@AllArgsConstructor
public class PublicFileController {
    private final ShareFileGetService sharedFileInfoListService;
    private final int maxSize;
    @GetMapping("list")
    public ResponseData list(@RequestParam @Nullable Integer uid,
                             @RequestParam int page, @RequestParam int size) {
        size = Math.min(maxSize, size);
        ResponseData responseData = ResponseData.success();
        responseData.setData(sharedFileInfoListService.list(uid, page, size));
        return responseData;
    }

    @GetMapping("search")
    public ResponseData search(@RequestParam @Nullable Integer uid, @RequestParam String keyword,
                               @RequestParam int page, @RequestParam int size) {
        size = Math.min(maxSize, size);
        ResponseData responseData = ResponseData.success();
        responseData.setData(sharedFileInfoListService.search(uid, keyword, page, size));
        return responseData;
    }
}
