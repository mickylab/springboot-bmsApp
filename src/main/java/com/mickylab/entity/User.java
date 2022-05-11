package com.mickylab.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "sys_user")
@ToString
public class User {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String username;
    // 前端不展示密码
    @JsonIgnore
    private String password;
    private String nickname;
    private String email;
    private String phone;
    private String address;
    // 数据库别名 @TableField(value = "")
    // mybatis-plus自动包含驼峰转换

}