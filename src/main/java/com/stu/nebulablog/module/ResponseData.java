package com.stu.nebulablog.module;

import lombok.Data;

@Data
public class ResponseData {
    private int code;
    private Object data;
    public static ResponseData success(){
        ResponseData responseData=new ResponseData();
        responseData.setCode(200);
        return responseData;
    }
    public static ResponseData fail(){
        ResponseData responseData=new ResponseData();
        responseData.setCode(404);
        return responseData;
    }
}
