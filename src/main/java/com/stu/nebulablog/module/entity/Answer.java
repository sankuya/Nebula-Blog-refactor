package com.stu.nebulablog.module.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("a")
public class Answer {
    @TableId(type = IdType.AUTO)
    private Integer aid;
    private Integer uid;
    private String content;
    @TableField(update = "now()",fill = FieldFill.INSERT_UPDATE)
    private Timestamp date;
    private Integer qid;
    private String author;
    private String username;
    public int hashCode(){
        return aid;
    }
}
