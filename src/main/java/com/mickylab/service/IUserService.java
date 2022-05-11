package com.mickylab.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mickylab.controller.dto.UserDTO;
import com.mickylab.entity.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mickylab
 * @since 2022-05-01
 */
public interface IUserService extends IService<User> {

    boolean login(UserDTO userDTO);

}