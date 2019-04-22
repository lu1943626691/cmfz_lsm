package com.baizhi.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.alibaba.fastjson.JSON;
import com.baizhi.entity.User;
import com.baizhi.service.ChinaService;
import com.baizhi.service.UserService;
import io.goeasy.GoEasy;
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
    public Map insert(MultipartFile img, User user, Integer sex) {
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
        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-30adc5117b494389ae69e971d734008a");
        goEasy.publish("lsm", JSON.toJSONString(userService.selectDate()));
        if (sex == 1) {
            goEasy.publish("man", JSON.toJSONString(chinaService.selectBySex(1)));
        } else {
            goEasy.publish("woman", JSON.toJSONString(chinaService.selectBySex(0)));
        }

        return map;
    }

    @RequestMapping("delete")
    public void delete(User user) {
        userService.delete(user);
        publish();
    }

    @RequestMapping("update")
    public void update(User user) {
        userService.update(user);
        publish();
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
        return userService.selectDate();
    }

    public void publish() {
        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-30adc5117b494389ae69e971d734008a");
        goEasy.publish("lsm", JSON.toJSONString(userService.selectDate()));
    }

    //推送性别
    public void publicBySex(Integer sex) {
        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-30adc5117b494389ae69e971d734008a");
        goEasy.publish("man", JSON.toJSONString(chinaService.selectBySex(sex)));
    }


    //地图
    @RequestMapping("distribution")
    public Map distribution(Integer sex) {
        return chinaService.selectBySex(sex);
    }
}
