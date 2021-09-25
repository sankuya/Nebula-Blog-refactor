package com.stu.nebulablog.module.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Data
public class Question implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer questionId;
    private Integer uid;
    private String title;
    private String author;
    private Integer status;
    private String content;
    @TableField(exist = false)
    private String summary;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date date;
    private Integer answerNum;

    public int hashCode() {
        return questionId;
    }
}
