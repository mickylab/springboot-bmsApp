package com.mickylab.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mickylab.common.Constants;
import com.mickylab.controller.dto.UserDTO;
import com.mickylab.exception.ServiceException;
import com.mickylab.mapper.UserMapper;
import com.mickylab.entity.User;
import com.mickylab.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author micky
 * @since 2022-05-01
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private static final Log LOG = Log.get();

    public UserDTO login(UserDTO userDTO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userDTO.getUsername());
        queryWrapper.eq("password", userDTO.getPassword());
        User one;

        try {
            one = getOne(queryWrapper);
        } catch (Exception e) {
            LOG.error(e);
            throw new ServiceException(Constants.CODE_500, "System Error");
        }

        if (one != null) {
            BeanUtil.copyProperties(one, userDTO, true);
            return userDTO;
        } else {
            throw new ServiceException(Constants.CODE_600, "Either the username or password is invalid!");
        }

    }
}
