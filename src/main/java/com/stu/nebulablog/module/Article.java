package com.stu.nebulablog.module;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class Article {
    @TableId(type = IdType.AUTO)
    private Integer art_id;
    private Integer uid;
    private String author;
    private String title;
    private String content;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date date;
    public int hashCode(){
        return art_id;
    }
}
