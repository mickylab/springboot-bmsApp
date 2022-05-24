package com.mickylab.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "sys_dict")
public class Dict {

    private String name;
    private String value;
    private String type;
}
