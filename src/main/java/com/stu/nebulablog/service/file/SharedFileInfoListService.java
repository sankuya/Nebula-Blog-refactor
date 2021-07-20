package com.stu.nebulablog.service.file;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stu.nebulablog.mapper.FileInfoMapper;
import com.stu.nebulablog.module.entity.FileInfo;
import com.stu.nebulablog.module.vo.PageDataVO;
import com.stu.nebulablog.utils.PageToMapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SharedFileInfoListService {
    @Autowired
    private FileInfoMapper fileInfoMapper;
    @Autowired
    private PageToMapUtil<FileInfo> fileInfoPageToMapUtil;

    @Cacheable(cacheNames = "fileInfoList")
    public PageDataVO<String> listSharedFileInfo(int page, int size) {
        Page<FileInfo> fileInfoPage = new Page<>(page, size);
        fileInfoMapper.selectPage(fileInfoPage, null);
        PageDataVO<FileInfo> res = fileInfoPageToMapUtil.getMapFromPageWithPages(fileInfoPage);
        return new PageDataVO<>(res.getSize(), res//FileInfo提取出path
                .getDetail()
                .stream()
                .map(fileInfo -> Paths.get("/user", fileInfo.getUsername(), "share", fileInfo.getFilename()).toString())
                .collect(Collectors.toList()));
    }
}
