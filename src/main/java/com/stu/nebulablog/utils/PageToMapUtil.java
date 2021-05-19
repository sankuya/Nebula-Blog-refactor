package com.stu.nebulablog.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class PageToMapUtil<T> {
    public Map<String ,Object>getMapFromPageWithPages(Page<T> page){
        Map<String, Object> res = page
                .getRecords()
                .stream()
                .collect(Collectors.toMap((e) -> String.valueOf(e.hashCode()), (e) -> e));
        res.put("maxPageNum", page.getPages());
        return res;
    }
}
