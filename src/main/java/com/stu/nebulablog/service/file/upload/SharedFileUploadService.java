package com.stu.nebulablog.service.file.upload;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.stu.nebulablog.mapper.FileInfoMapper;
import com.stu.nebulablog.mapper.UserInfoMapper;
import com.stu.nebulablog.module.entity.FileInfo;
import com.stu.nebulablog.module.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SharedFileUploadService extends AbstractSharedFileUploadService {
    private final FileInfoMapper fileInfoMapper;
    private final UserInfoMapper userInfoMapper;

    public SharedFileUploadService(String preUrl, FileInfoMapper fileInfoMapper, UserInfoMapper userInfoMapper) {
        super(preUrl);
        this.fileInfoMapper = fileInfoMapper;
        this.userInfoMapper = userInfoMapper;
    }

    @CacheEvict(cacheNames = "fileInfoList", allEntries = true)
    public boolean uploadSharedFile(int uid, MultipartFile multipartFile) {
        String username = userInfoMapper.selectOne(
                new LambdaQueryWrapper<UserInfo>()
                        .select(UserInfo::getUsername)
                        .eq(UserInfo::getUid, uid))
                .getUsername();
        FileInfo fileInfo = new FileInfo();
        fileInfo.setUid(uid);
        fileInfo.setFilename(multipartFile.getOriginalFilename());
        FileInfo tmpFileInfo;
        if ((tmpFileInfo = fileInfoMapper.selectOne(
                new LambdaQueryWrapper<FileInfo>()
                        .select(FileInfo::getFileId)
                        .eq(FileInfo::getUid, fileInfo.getUid())
                        .eq(FileInfo::getFilename, fileInfo.getFilename())
        )) != null) {
            fileInfo.setFileId(tmpFileInfo.getFileId());
            fileInfoMapper.updateById(fileInfo);
        } else {
            fileInfoMapper.insert(fileInfo);
        }
        super.uploadFile(username, multipartFile);
        return false;
    }
}
