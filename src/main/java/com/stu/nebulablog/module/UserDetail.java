package com.stu.nebulablog.module;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class UserDetail {
    @TableId(type = IdType.AUTO)
    private Integer uid;
    private String nickname;
    private String blogname;
    private String motto;
    private String gender;
    private String hobby;
    private String QQ;
    private String location;
    public int hashCode(){
        return uid;
    }
}
