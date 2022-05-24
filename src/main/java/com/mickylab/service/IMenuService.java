package com.mickylab.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mickylab.entity.Menu;

import java.util.List;

public interface IMenuService extends IService<Menu> {

    List<Menu> findMenus(String name);
}
