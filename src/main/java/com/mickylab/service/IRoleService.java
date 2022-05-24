package com.mickylab.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mickylab.entity.Role;

import java.util.List;

public interface IRoleService extends IService<Role> {

    void setRoleMenu(Integer roleId, List<Integer> menuIds);

    List<Integer> getRoleMenu(Integer roleId);
}
