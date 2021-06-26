package com.stu.nebulablog.module.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;
import java.sql.Timestamp;

@Data
public class Article {
    @TableId(type = IdType.AUTO,value = "art_id")
    private Integer articleId;
    private int uid;
    private String author;
    private String title;
    private String content;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date date;
    public int hashCode(){
        return articleId;
    }
}
