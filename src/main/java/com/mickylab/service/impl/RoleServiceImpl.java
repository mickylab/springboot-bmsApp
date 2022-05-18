package com.mickylab.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mickylab.entity.Role;
import com.mickylab.mapper.RoleMapper;
import com.mickylab.service.IRoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
