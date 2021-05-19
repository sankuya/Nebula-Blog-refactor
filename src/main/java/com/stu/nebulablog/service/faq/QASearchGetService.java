package com.stu.nebulablog.service.faq;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stu.nebulablog.mapper.QMapper;
import com.stu.nebulablog.module.Q;
import com.stu.nebulablog.utils.PageToMapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class QASearchGetService {
    @Autowired
    private QMapper qMapper;
    @Autowired
    private PageToMapUtil pageToMapUtil;

    public Map<String, Object> doASSearch(String keyword, int page, int size) {
        Page<Q> qPage = new Page<>(page, size);
        QueryWrapper<Q> qQueryWrapper = new QueryWrapper<>();
        qQueryWrapper
                .select("q_id","title","author","date","answer","status")
                .like("title",keyword)
                .or()
                .like("author",keyword)
                .orderByDesc("q_id");
        return pageToMapUtil.getMapFromPageWithPages(qMapper.selectPage(qPage, qQueryWrapper));
    }
}
