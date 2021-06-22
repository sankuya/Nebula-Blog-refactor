package com.stu.nebulablog.module.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
@TableName("a")
public class Answer {
    @TableId(type = IdType.AUTO,value = "aid")
    private Integer answerId;
    private Integer uid;
    private String content;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date date;
    @TableField(value = "qid")
    private Integer questionId;
    private String author;
    private String username;
    public int hashCode(){
        return answerId;
    }
}
