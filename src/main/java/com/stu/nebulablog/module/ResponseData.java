package com.stu.nebulablog.module;

import lombok.Data;

@Data
public class ResponseData {
    private int code;
    private Object data;
    public static ResponseData success(){
        ResponseData responseData=new ResponseData();
        responseData.setCode(1);
        return responseData;
    }
    public static ResponseData fail(){
        ResponseData responseData=new ResponseData();
        responseData.setCode(0);
        return responseData;
    }
}
