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
    @TableId(type = IdType.AUTO,value = "file_id")
    private Integer fileId;
    private Integer uid;
    private String filename;
    private String username;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date date;
}
