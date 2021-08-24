package com.stu.nebulablog.service.file.upload;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.stu.nebulablog.mapper.FileInfoMapper;
import com.stu.nebulablog.mapper.UserInfoMapper;
import com.stu.nebulablog.module.entity.FileInfo;
import com.stu.nebulablog.module.entity.UserInfo;
import com.stu.nebulablog.service.file.AbstractFileService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Transactional(rollbackFor = IOException.class)
public class SharedFileDeleteService extends AbstractFileService {
    private final UserInfoMapper userInfoMapper;
    private final FileInfoMapper fileInfoMapper;

    public SharedFileDeleteService(String preUrl, UserInfoMapper userInfoMapper, FileInfoMapper fileInfoMapper) {
        super(preUrl);
        this.userInfoMapper = userInfoMapper;
        this.fileInfoMapper = fileInfoMapper;
    }

    @CacheEvict(cacheNames = "fileInfoList", allEntries = true)
    public boolean deleteSharedFile(int uid, int fileId) {
        String username, filename;
        try {
            username = userInfoMapper.selectOne(
                    new LambdaQueryWrapper<UserInfo>()
                            .eq(UserInfo::getUid, uid)
                            .select(UserInfo::getUsername)
            ).getUsername();
            filename = fileInfoMapper.selectOne(
                    new LambdaQueryWrapper<FileInfo>()
                            .eq(FileInfo::getFileId, fileId)
                            .select(FileInfo::getFilename)
            ).getFilename();
        } catch (NullPointerException e) {
            return false;
        }
        if (filename == null || fileInfoMapper.deleteById(fileId) != 1) return false;
        Path filePath = Paths.get(preUrl, username, "share", filename);
        try {
            Files.delete(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        return true;
    }
}
