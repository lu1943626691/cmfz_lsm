package com.baizhi.controller;

import com.baizhi.entity.Menu;
import com.baizhi.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @RequestMapping("selectAll")

    public List<Menu> selectAll(Map map) {
        List<Menu> menus = menuService.selectAll();
        for (Menu menu : menus) {
            List<Menu> menus1 = menuService.selectById(menu.getId());
            menu.setList(menus1);

        }
        return menus;

    }

}
