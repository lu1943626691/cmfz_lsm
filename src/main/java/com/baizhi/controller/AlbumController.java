package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import com.baizhi.service.AlbumService;
import com.baizhi.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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
}
