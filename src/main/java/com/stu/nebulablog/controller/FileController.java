package com.stu.nebulablog.controller;

import com.stu.nebulablog.module.ResponseData;
import com.stu.nebulablog.service.file.upload.SharedFileDeleteService;
import com.stu.nebulablog.service.file.upload.SharedFileUploadService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("file")@AllArgsConstructor
public class FileController {
    private final SharedFileUploadService sharedFileUploadService;
    private final SharedFileDeleteService sharedFileDeleteService;

    @PostMapping("/upload")
    public ResponseData upload(HttpServletRequest httpServletRequest, HttpSession session) {
        MultipartFile multipartFile;
        try {
            multipartFile = ((MultipartHttpServletRequest) httpServletRequest).getFiles("file").get(0);
        } catch (ClassCastException | IndexOutOfBoundsException e) {
            return ResponseData.fail();
        }
        Integer uid = (Integer) session.getAttribute("uid");
        sharedFileUploadService.uploadSharedFile(uid, multipartFile);
        return ResponseData.success();
    }

    @PostMapping("/delete")
    public ResponseData delete(@RequestBody Map<String, Integer> src, HttpSession session) {
        Integer uid = (Integer) session.getAttribute("uid");
        Integer fileId = src.get("fileId");
        if (uid == null || fileId == null) return ResponseData.fail();
        if (sharedFileDeleteService.deleteSharedFile(uid, fileId)) return ResponseData.success();
        return ResponseData.fail();
    }
}
