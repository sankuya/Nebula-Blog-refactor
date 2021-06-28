package com.stu.nebulablog.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class PageToMapUtil<T> {
    public Map<String ,Object>getMapFromPageWithPages(Page<T> page){
        List<T> data=new ArrayList<>(page.getRecords());
        Map<String ,Object  >res=new HashMap<>();
        res.put("detail",data);
        res.put("maxPageNum", page.getPages());
        return res;
    }
}
