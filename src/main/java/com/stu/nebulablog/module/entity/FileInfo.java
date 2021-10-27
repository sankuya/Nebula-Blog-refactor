package com.stu.nebulablog.module.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class FileInfo implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer fileId;
    private Integer uid;
    @TableField(exist = false)
    private String username;
    private String filename;
    private String introduction;
    private String link;
    private String verify;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date date;
}
