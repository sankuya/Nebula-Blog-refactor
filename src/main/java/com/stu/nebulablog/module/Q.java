package com.stu.nebulablog.module;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class Q {
    @TableId(type = IdType.AUTO)
    private Integer q_id;
    private Integer uid;
    private String title;
    private String author;
    private Integer status;
    private String content;
    @TableField(update = "now()",fill = FieldFill.INSERT_UPDATE)
    private Date date;
    private Integer answer;
    public int hashCode(){
        return q_id;
    }
}
