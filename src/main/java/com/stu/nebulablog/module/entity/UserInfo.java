package com.stu.nebulablog.module.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class UserInfo implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer uid;
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String tel;
    @NotNull
    private String mail;
    public int hashCode(){
        return uid;
    }
}
