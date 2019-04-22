package com.baizhi.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.entity.China;
import com.baizhi.entity.User;
import com.baizhi.service.ChinaService;
import com.baizhi.service.UserService;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ChinaService chinaService;

    //查看所有
    @RequestMapping("selectAll")
    public Map selectAll(Integer page, Integer rows) {
        return userService.selectAll(page, rows);
    }

    //注册
    @RequestMapping("insert")
    public Map insert(MultipartFile img, User user) {
        Map map = new HashMap();
        String oldName = img.getOriginalFilename();
        String extension = FilenameUtils.getExtension(oldName);
        String uuid = UUID.randomUUID().toString();
        String newName = uuid + "." + extension;
        File file = new File("D:\\resources\\cmfz_lsm\\src\\main\\webapp\\userImg\\" + newName);
        user.setHeadImg("/userImg/" + newName);
        try {
            img.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        user.setCreateDate(new Date());
        user.setStatus("on");
        try {
            userService.insert(user);
            map.put("isAdd", "true");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("isAdd", "false");
        }
        return map;
    }

    @RequestMapping("delete")
    public void delete(User user) {
        userService.delete(user);
    }

    @RequestMapping("update")
    public void update(User user) {
        userService.update(user);
    }

    //导出
    @RequestMapping("selectExl")
    public void selectExl(HttpServletResponse response, HttpServletRequest request) {
        List<User> list = userService.select();
        for (User user : list) {
            user.setHeadImg(request.getSession().getServletContext().getRealPath("/") + user.getHeadImg());
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("用户", "user"),
                User.class, list);
        response.setHeader("Content-Disposition", "attachment; filename=user.xsl");
        response.setContentType("application/octet-stream, charset=utf-8");
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            workbook.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //柱状图
    @RequestMapping("activeUser")
    public Map activeUser() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("intervals", new String[]{"7天", "15天", "30天", "三个月", "半年", "一年"});
        map.put("counts", new int[]{userService.selectDate(7), userService.selectDate(15), userService.selectDate(30), userService.selectDate(100), userService.selectDate(200), userService.selectDate(365)});
        System.out.println(map);
        return map;

    }

    //地图
    @RequestMapping("distribution")
    public Map distribution(Integer sex) {
        List<China> list = chinaService.selectBySex(sex);
        Map map = new HashMap();
        map.put("china", list);
        System.out.println(map);
        return map;

    }
}
