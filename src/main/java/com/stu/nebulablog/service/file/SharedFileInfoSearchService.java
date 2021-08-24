package com.stu.nebulablog.service.file;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stu.nebulablog.mapper.FileInfoMapper;
import com.stu.nebulablog.module.entity.FileInfo;
import com.stu.nebulablog.module.vo.PageDataVO;
import com.stu.nebulablog.utils.PageToMapUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SharedFileInfoSearchService {
    private final FileInfoMapper fileInfoMapper;
    private final PageToMapUtil<FileInfo> fileInfoPageToMapUtil;

    @Cacheable(cacheNames = "FileInfoList")
    public PageDataVO<String> searchSharedFileInfo(int page, int size, String keyword) {
        Page<FileInfo> fileInfoPage = new Page<>(page, size);
        fileInfoMapper.selectPage(fileInfoPage,
                new LambdaQueryWrapper<FileInfo>()
                        .like(FileInfo::getFilename, keyword)
                        .or()
                        .like(FileInfo::getUsername, keyword)
        );
        PageDataVO<FileInfo> res = fileInfoPageToMapUtil.getMapFromPageWithPages(fileInfoPage);
        return new PageDataVO<>(res.getSize(), res//FileInfo提取出path
                .getDetail()
                .stream()
                .map(fileInfo -> Paths.get("/user", fileInfo.getUsername(), "share", fileInfo.getFilename()).toString())
                .collect(Collectors.toList()));
    }
}
