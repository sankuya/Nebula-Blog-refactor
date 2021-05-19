package com.stu.nebulablog.module;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class UserInfo {
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
