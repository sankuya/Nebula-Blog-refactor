package com.stu.nebulablog.service.file.share;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.stu.nebulablog.mapper.FileInfoMapper;
import com.stu.nebulablog.module.entity.FileInfo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ShareFileDeleteService {
    private final FileInfoMapper fileInfoMapper;

    public boolean delete(int uid, int fileId) {
        return fileInfoMapper.delete(new LambdaQueryWrapper<FileInfo>()
                .eq(FileInfo::getUid, uid)
                .eq(FileInfo::getFileId, fileId)) > 0;
    }
}
