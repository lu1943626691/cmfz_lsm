package com.baizhi.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import com.baizhi.service.AlbumService;
import com.baizhi.service.ChapterService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@RestController
@RequestMapping("album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;
    @Autowired
    private ChapterService chapterService;

    @RequestMapping("select")
    public List<Album> selectAll() {
        List<Album> albums = albumService.selectAll();
        for (Album album : albums) {
            List<Chapter> chapters = chapterService.selectById(album.getId());
            album.setChildren(chapters);
        }
        System.out.println(albums);
        return albums;

    }

    @RequestMapping("selectOne")
    public Map selectById(Integer id) {
        Map map = new HashMap();
        try {
            Album album = albumService.selectById(id);
            map.put("album", album);
            map.put("isSelectOne", "true");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("isSelectOne", "false");
        }
        return map;
    }

    @RequestMapping("insert")
    public Map insert(MultipartFile file, Album album) throws IOException {
        //老名字
        String oldName = file.getOriginalFilename();
        //新名字
        String s = UUID.randomUUID().toString();
        String newName = s + oldName.substring(oldName.lastIndexOf("."));
        System.out.println(newName);
        file.transferTo(new File("D:\\resources\\cmfz_lsm\\src\\main\\webapp\\audioCollection\\" + newName));
        album.setImgPath("/audioCollection/" + newName);
        album.setPublishDate(new Date());
        Map map = new HashMap();
        try {
            albumService.insert(album);
            map.put("isAdd", "true");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("isAdd", "false");
        }
        return map;
    }

    @RequestMapping("selectAlbum")
    public List<Album> selectAlbum() {
        Map map = new HashMap();
        List<Album> albums = albumService.selectAll();
        return albums;
    }

    @RequestMapping("selectExl")
    public void selectExl(HttpServletResponse res, HttpServletRequest request) throws IOException {
        List<Album> albums = albumService.selectAll();
        for (Album album : albums) {
            List<Chapter> chapters = chapterService.selectById(album.getId());
            String imgPath = album.getImgPath();
            album.setImgPath(request.getSession().getServletContext().getRealPath("/") + imgPath);
            System.out.println("路径" + request.getSession().getServletContext().getRealPath("/"));
            album.setChildren(chapters);
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("专辑列表", "专辑"),
                Album.class, albums);
        ByteArrayOutputStream fos = null;
        byte[] retArr = null;
        try {
            fos = new ByteArrayOutputStream();
            workbook.write(fos);
            retArr = fos.toByteArray();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        OutputStream os = res.getOutputStream();
        try {
            res.reset();
            res.setHeader("Content-Disposition", "attachment; filename=album.xls");//要保存的文件名
            res.setContentType("application/octet-stream; charset=utf-8");
            os.write(retArr);
            os.flush();
        } finally {
            if (os != null) {
                os.close();
            }
        }

    }
}
