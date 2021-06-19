package com.stu.nebulablog.module.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class Article {
    @TableId(type = IdType.AUTO)
    private Integer art_id;
    private Integer uid;
    private String author;
    private String title;
    private String content;
    @TableField(update = "now()",fill = FieldFill.INSERT_UPDATE)
    private Timestamp date;
    public int hashCode(){
        return art_id;
    }
}
