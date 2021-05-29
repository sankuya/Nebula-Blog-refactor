package com.stu.nebulablog.module;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class A{
    @TableId(type = IdType.AUTO)
    private Integer aid;
    private Integer uid;
    private String content;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date date;
    private Integer qid;
    private String author;
    private String username;
    public int hashCode(){
        return aid;
    }
}
