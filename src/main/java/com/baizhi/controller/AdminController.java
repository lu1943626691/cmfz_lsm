package com.baizhi.controller;

import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @RequestMapping("login")
    public Map login(Admin admin, Map map) {
        System.out.println("开始");
        Map map1 = new HashMap();
        Admin admin1 = adminService.selectOne(admin);
        System.out.println(admin.getPassword());
        System.out.println(admin.getName());
        if (admin1 == null) {
            map.put("isLogin", "false");
            return map1;
        } else {
            map.put("isLogin", "true");
            map.put("admin", admin1.getName());
            return map1;
        }
    }
}
