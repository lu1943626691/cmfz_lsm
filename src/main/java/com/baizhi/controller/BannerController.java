package com.baizhi.controller;

import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;

    @RequestMapping("selectAll")
    public Map selectAll(Integer page, Integer rows) {

        return bannerService.selectAll(page, rows);
    }

    @RequestMapping("insert")
    public Map insert(MultipartFile file1, Banner banner, HttpServletRequest request) throws IOException {
        //老名字
        String oldName = file1.getOriginalFilename();
        //新名字
        String s = UUID.randomUUID().toString();

        String newName = s + oldName.substring(oldName.lastIndexOf("."));
        System.out.println(newName);
        file1.transferTo(new File("D:\\resources\\cmfz_lsm\\src\\main\\webapp\\imgs\\" + newName));
        banner.setImg_path("/imgs/" + newName);
        banner.setCreate_date(new Date());
        System.out.println(banner);
        Map map = new HashMap();
        try {
            bannerService.insert(banner);
            map.put("isAdd", "true");
        } catch (Exception e) {
            map.put("isAdd", "false");
            e.printStackTrace();

        }
        return map;
    }

    @RequestMapping("delete")
    public Map delete(Banner banner) {

        System.out.println("sschu");
        Map map = new HashMap();
        try {
            bannerService.delete(banner);
            map.put("isDelete", "true");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("isDelete", "false");
        }
        return map;
    }

    @RequestMapping("update")
    public Map update(MultipartFile file, Banner banner) throws IOException {
        if (file != null) {
            //老名字
            String oldName = file.getOriginalFilename();
            //新名字
            String s = UUID.randomUUID().toString();

            String newName = s + oldName.substring(oldName.lastIndexOf("."));
            System.out.println(newName);
            file.transferTo(new File("D:\\resources\\cmfz_lsm\\src\\main\\webapp\\imgs\\" + newName));
            banner.setImg_path("/imgs/" + newName);
        }
        Map map = new HashMap();
        try {
            bannerService.update(banner);
            map.put("isUpdate", "true");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("isUpdate", "false");
        }
        return map;
    }
}
