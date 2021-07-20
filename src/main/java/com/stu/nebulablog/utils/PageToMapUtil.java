package com.stu.nebulablog.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stu.nebulablog.module.vo.PageDataVO;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class PageToMapUtil<T> {
    public PageDataVO<T> getMapFromPageWithPages(Page<T> page){
        List<T> data=new ArrayList<>(page.getRecords());
        return new PageDataVO<T>(page.getPages(),data);
    }
}
