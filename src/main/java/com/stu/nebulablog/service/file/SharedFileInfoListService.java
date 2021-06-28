package com.stu.nebulablog.service.file;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stu.nebulablog.mapper.FileInfoMapper;
import com.stu.nebulablog.module.entity.FileInfo;
import com.stu.nebulablog.utils.PageToMapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SharedFileInfoListService {
    @Autowired
    private FileInfoMapper fileInfoMapper;
    @Autowired
    private PageToMapUtil<FileInfo> fileInfoPageToMapUtil;
    @Cacheable(cacheNames = "fileInfoList")
    public Map<String, Object> listSharedFileInfo(int page, int size) {
        Page<FileInfo> fileInfoPage = new Page<>(page, size);
        fileInfoMapper.selectPage(fileInfoPage, null);
        Map<String, Object> res = fileInfoPageToMapUtil.getMapFromPageWithPages(fileInfoPage);
        List<FileInfo> fileInfos = (List<FileInfo>) res.get("detail");
        List<String > paths = new ArrayList<>();
        fileInfos.forEach(file ->
                paths.add(Paths.get("user",file.getUsername(), "share", file.getFilename()).toString())
        );
        res.put("detail", paths);
        return res;
    }
}
