package com.mickylab.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mickylab.mapper.UserMapper;
import com.mickylab.pojo.User;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {
    public boolean saveUser(User user) {
        return saveOrUpdate(user); // mybatis-plus提供的方法
    }
}
