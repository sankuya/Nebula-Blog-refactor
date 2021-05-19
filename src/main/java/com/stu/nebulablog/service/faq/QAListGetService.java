package com.stu.nebulablog.service.faq;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stu.nebulablog.mapper.ArticleMapper;
import com.stu.nebulablog.mapper.QMapper;
import com.stu.nebulablog.module.Q;
import com.stu.nebulablog.utils.PageToMapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class QAListGetService {
    @Autowired
    private QMapper qMapper;
    @Autowired
    private PageToMapUtil<Q> qPageToMapUtil;

    public Map<String, Object> doGetQAList(int size, int page) {
        Page<Q> qPage = new Page<>(page, size);
        qMapper.selectPage(qPage, null);
        return qPageToMapUtil.getMapFromPageWithPages(qPage);
    }
}
