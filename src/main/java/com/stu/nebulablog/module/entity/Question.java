package com.stu.nebulablog.module.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Data
@TableName("q")
public class Question implements Serializable {
    @TableId(type = IdType.AUTO,value = "q_id")
    private Integer questionId;
    private Integer uid;
    private String title;
    private String author;
    private Integer status;
    private String content;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date date;
    private Integer answer;
    public int hashCode(){
        return questionId;
    }
}
