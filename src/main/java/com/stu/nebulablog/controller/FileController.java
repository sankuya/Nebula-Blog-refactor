package com.stu.nebulablog.controller;

import com.stu.nebulablog.module.ResponseData;
import com.stu.nebulablog.module.entity.FileInfo;
import com.stu.nebulablog.service.file.share.ShareFileDeleteService;
import com.stu.nebulablog.service.file.share.ShareFileUploadService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("file")
@AllArgsConstructor
public class FileController {
    private final ShareFileUploadService sharedFileUploadService;
    private final ShareFileDeleteService shareFileDeleteService;

    @PostMapping("/upload")
    public ResponseData upload(@SessionAttribute Integer uid,@RequestBody FileInfo fileInfo) {
        fileInfo.setUid(uid);
        if (sharedFileUploadService.upload(fileInfo)) return ResponseData.success();
        return ResponseData.fail();
    }

    @PostMapping("/delete")
    public ResponseData delete(@SessionAttribute Integer uid, @RequestBody Map<String, Integer> src) {
        Integer fileId = src.get("fileId");
        if (uid == null || fileId == null) return ResponseData.fail();
        if (shareFileDeleteService.delete(uid, fileId)) return ResponseData.success();
        return ResponseData.fail();
    }
}
