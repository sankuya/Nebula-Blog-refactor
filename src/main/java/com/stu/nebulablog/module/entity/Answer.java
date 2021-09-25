package com.stu.nebulablog.module.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Data
public class Answer implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer answerId;
    private Integer uid;
    private String content;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date date;
    private Integer questionId;
    private String author;
    private String username;
    @TableField(exist = false)
    private String summary;

    public int hashCode() {
        return answerId;
    }
}
