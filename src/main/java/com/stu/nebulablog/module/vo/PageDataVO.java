package com.stu.nebulablog.module.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageDataVO<T> implements Serializable {
    private long size;
    private List<T> detail;
}
