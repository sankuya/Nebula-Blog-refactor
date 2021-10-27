package com.stu.nebulablog.module.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.sql.Timestamp;

@Data
public class Article implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer articleId;
    private int uid;
    @TableField(exist = false)
    private String author;
    private String title;
    private String content;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date date;
    @TableField(exist = false)
    private String summary;

    public int hashCode() {
        return articleId;
    }
}
