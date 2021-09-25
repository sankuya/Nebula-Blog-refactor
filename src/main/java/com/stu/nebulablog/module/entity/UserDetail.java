package com.stu.nebulablog.module.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserDetail implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer uid;
    private String nickname;
    private String blogname;
    private String motto;
    private String gender;
    private String hobby;
    private String qq;
    private String location;
    public int hashCode(){
        return uid;
    }
}
