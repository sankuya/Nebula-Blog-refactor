package com.stu.nebulablog.module.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
@TableName("q")
public class Question {
    @TableId(type = IdType.AUTO)
    private Integer q_id;
    private Integer uid;
    private String title;
    private String author;
    private Integer status;
    private String content;
    @TableField(update = "now()",fill = FieldFill.INSERT_UPDATE)
    private Date date;
    private Integer answer;
    public int hashCode(){
        return q_id;
    }
}
