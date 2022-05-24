package com.mickylab.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.generator.config.INameConvert;
import com.mickylab.common.Constants;
import com.mickylab.controller.dto.UserDTO;
import com.mickylab.entity.Menu;
import com.mickylab.exception.ServiceException;
import com.mickylab.mapper.RoleMapper;
import com.mickylab.mapper.RoleMenuMapper;
import com.mickylab.mapper.UserMapper;
import com.mickylab.entity.User;
import com.mickylab.service.IMenuService;
import com.mickylab.service.IUserService;
import com.mickylab.utils.TokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private IMenuService menuService;

    public UserDTO login(UserDTO userDTO) {
        User one = getUserInfo(userDTO);
        if (one != null) {
            BeanUtil.copyProperties(one, userDTO, true);
            // 设置token
            String token = TokenUtils.getToken(one.getId().toString(), one.getPassword());
            userDTO.setToken(token);

            String role = one.getRole(); // ROLE_ADMIN
            // 设置用户的菜单列表
            List<Menu> roleMenus = getRoleMenus(role);
            userDTO.setMenus(roleMenus);

            return userDTO;
        } else {
            throw new ServiceException(Constants.CODE_600, "Either the username or password is invalid!");
        }

    }

    @Override
    public User signup(UserDTO userDTO) {
        User one = getUserInfo(userDTO);
        if (one == null) {
            one = new User();
            BeanUtil.copyProperties(userDTO, one, true);
            save(one);  // 把 copy完之后的用户对象存储到数据库
        } else {
            throw new ServiceException(Constants.CODE_600, "Username already exists");
        }
        return one;
    }

    private User getUserInfo(UserDTO userDTO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userDTO.getUsername());
        queryWrapper.eq("password", userDTO.getPassword());
        User one;
        try {
            one = getOne(queryWrapper); // 从数据库查询用户信息
        } catch (Exception e) {
            LOG.error(e);
            throw new ServiceException(Constants.CODE_500, "系统错误");
        }
        return one;
    }

    /**
     * 获取当前角色的菜单列表
     * @param roleFlag user.role
     * @return role menus
     */
    private List<Menu> getRoleMenus(String roleFlag) {
        Integer roleId = roleMapper.selectByFlag(roleFlag);
        // 当前角色所有菜单id的集合
        List<Integer> menuIds = roleMenuMapper.selectByRoleId(roleId);
        // 查出所有菜单(树形)
        List<Menu> menus = menuService.findMenus("");
        // 筛选当前用户角色的菜单
        List<Menu> roleMenus = new ArrayList<>(); // 最后筛选完成后的list
        for (Menu menu: menus) {
            if (menuIds.contains(menu.getId())) roleMenus.add(menu);
            List<Menu> children = menu.getChildren();
            // 移出children里面不在menuIds集合中的元素
            children.removeIf(child -> !menuIds.contains(child.getId()));
        }
        return roleMenus;
    }
}
