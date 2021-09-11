package com.stu.nebulablog.service.file.share;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stu.nebulablog.mapper.FileInfoMapper;
import com.stu.nebulablog.module.entity.FileInfo;
import com.stu.nebulablog.module.vo.PageDataVO;
import com.stu.nebulablog.utils.PageToMapUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ShareFileGetService {
    private final FileInfoMapper fileInfoMapper;
    private final PageToMapUtil<FileInfo> fileInfoPageToMapUtil;

    public PageDataVO<FileInfo> list(Integer uid, int page, int size) {
        Page<FileInfo> fileInfoPage = new Page<>(page, size);
        fileInfoMapper.selectPage(fileInfoPage, new LambdaQueryWrapper<FileInfo>()
                .eq(Objects.nonNull(uid), FileInfo::getUid, uid)
                .select(FileInfo::getFileId));
        PageDataVO<FileInfo> fileInfoPageDataVO = fileInfoPageToMapUtil.getMapFromPageWithPages(fileInfoPage);
        fileInfoPageDataVO.setDetail(fileInfoPageDataVO.getDetail().stream().map(fileInfo ->
                this.get(fileInfo.getFileId())).collect(Collectors.toList()));
        return fileInfoPageDataVO;
    }

    public PageDataVO<FileInfo> search(Integer uid, String keyword, int page, int size) {
        Page<FileInfo> fileInfoPage = new Page<>(page, size);
        fileInfoMapper.selectPage(fileInfoPage, new LambdaQueryWrapper<FileInfo>()
                .eq(Objects.nonNull(uid), FileInfo::getUid, uid)
                .and(fileInfoLambdaQueryWrapper -> fileInfoLambdaQueryWrapper
                        .like(FileInfo::getIntroduction, keyword)
                        .or()
                        .like(FileInfo::getFilename, keyword))
                .select(FileInfo::getFileId));
        PageDataVO<FileInfo> fileInfoPageDataVO = fileInfoPageToMapUtil.getMapFromPageWithPages(fileInfoPage);
        fileInfoPageDataVO.setDetail(fileInfoPageDataVO.getDetail().stream().map(fileInfo ->
                this.get(fileInfo.getFileId())).collect(Collectors.toList()));
        return fileInfoPageDataVO;
    }

    public FileInfo get(int fileId) {
        return fileInfoMapper.selectById(fileId);
    }
}
