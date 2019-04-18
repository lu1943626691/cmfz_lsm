package com.baizhi.service;

import com.baizhi.entity.Menu;

import java.util.List;

public interface MenuService {
    public List<Menu> selectAll();

    public List<Menu> selectById(Integer id);
}
