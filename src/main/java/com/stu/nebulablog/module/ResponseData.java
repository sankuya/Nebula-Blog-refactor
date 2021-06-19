package com.stu.nebulablog.module;

import lombok.Data;

@Data
public class ResponseData {
    private int code;
    private String msg;
    private Object data;
}
