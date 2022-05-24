package com.mickylab.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "sys_role_menu")
public class RoleMenu {

    private Integer roleId;
    private Integer MenuId;
}
