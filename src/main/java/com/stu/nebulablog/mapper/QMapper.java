package com.stu.nebulablog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stu.nebulablog.module.entity.Q;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface QMapper extends BaseMapper<Q> {
}
