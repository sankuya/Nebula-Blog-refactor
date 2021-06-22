package com.stu.nebulablog.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PageToMapUtil<T> {
    public Map<String ,Object>getMapFromPageWithPages(Page<T> page){
        Set<T>data=new HashSet<>(page.getRecords());
        Map<String ,Object  >res=new HashMap<>();
        res.put("detail",data);
//        Map<String, Object> res = page
//                .getRecords()
//                .stream()
//                .collect(Collectors.toMap((e) -> String.valueOf(e.hashCode()), (e) -> e));
        res.put("maxPageNum", page.getPages());
        return res;
    }
}
