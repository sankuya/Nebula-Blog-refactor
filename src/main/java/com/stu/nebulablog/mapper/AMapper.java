package com.stu.nebulablog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stu.nebulablog.module.entity.A;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AMapper extends BaseMapper<A> {
}
