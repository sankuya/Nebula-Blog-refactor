package com.stu.nebulablog.service.file.share;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.stu.nebulablog.mapper.FileInfoMapper;
import com.stu.nebulablog.mapper.UserDetailMapper;
import com.stu.nebulablog.module.entity.FileInfo;
import com.stu.nebulablog.module.entity.UserDetail;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ShareFileUploadService {
    private final FileInfoMapper fileInfoMapper;
    private final UserDetailMapper userDetailMapper;
    public boolean upload(FileInfo fileInfo){
        UserDetail userDetail=userDetailMapper.selectOne(new LambdaQueryWrapper<UserDetail>()
        .eq(UserDetail::getUid,fileInfo.getUid())
        .select(UserDetail::getNickname));
        fileInfo.setUsername(Optional.ofNullable(userDetail).map(UserDetail::getNickname).orElse(null));
        if(fileInfoMapper.insert(fileInfo)!=0)return true;
        return false;
    }
}
