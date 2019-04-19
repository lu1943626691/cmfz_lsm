package com.baizhi.controller;

import com.baizhi.entity.Chapter;
import com.baizhi.service.ChapterService;
import com.baizhi.utils.AudioUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("chapter")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;

    //生成uuid
    //UUID.randomUUID().toString().replace("-", "").toLowerCase();
    @RequestMapping("insert")
    public Map insert(MultipartFile audio, String title, Integer albumId, HttpSession session) throws Exception {
        //设置id
        String id = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        System.out.println(id);
        Chapter chapter = new Chapter();
        chapter.setId(id);
        //获取文件大小
        long size = audio.getSize();
        String printSize = getPrintSize(size);
        System.out.println("音频大小" + printSize);
        chapter.setSize(printSize);
        //设置所属专辑
        chapter.setAlbumId(albumId);
        //获取文件上传的目录
        /*String realPath=session.getServletContext().getRealPath("/");
        String dir=realPath+"audio";
        File file=new File(dir);
       if(!file.exists()){
           //如果不存在，就创建一个文件夹
           file.mkdir();
       }*/
        //获取源文件名称
        String oldName = audio.getOriginalFilename();
        //获取扩展名
        String extension = FilenameUtils.getExtension(oldName);
        String newName = UUID.randomUUID().toString();
        newName = newName + "." + extension;
        /* File file1=null;
        try {
            file1=new File(dir,newName);
            audio.transferTo(file1);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        File file = new File("D:\\resources\\cmfz_lsm\\src\\main\\webapp\\audio\\" + newName);
        audio.transferTo(file);
        chapter.setChapterUrl("/audio/" + newName);
        Map map = new HashMap();
        //时长
        Long duration = AudioUtil.getDuration(file);
        System.out.println(duration);
        String s = formatDuring(duration);
        System.out.println(s);
        chapter.setDuration(s);
        //名称
        chapter.setTitle(title);
        try {
            chapterService.insert(chapter);
            map.put("isInsert", "true");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("isInsert", "false");
        }
        return map;
    }

    //计算时长
    public static String getPrintSize(long size) {
        //如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义
        if (size < 1024) {
            return String.valueOf(size) + "B";
        } else {
            size = size / 1024;
        }
        //如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
        //因为还没有到达要使用另一个单位的时候
        //接下去以此类推
        if (size < 1024) {
            return String.valueOf(size) + "KB";
        } else {
            size = size / 1024;
        }
        if (size < 1024) {
            //因为如果以MB为单位的话，要保留最后1位小数，
            //因此，把此数乘以100之后再取余
            size = size * 100;
            return String.valueOf((size / 100)) + "."
                    + String.valueOf((size % 100)) + "MB";
        } else {
            //否则如果要以GB为单位的，先除于1024再作同样的处理
            size = size * 100 / 1024;
            return String.valueOf((size / 100)) + "."
                    + String.valueOf((size % 100)) + "GB";
        }
    }

    //转换时长
    public static String formatDuring(long mss) {
        long hours = (mss % (1000 * 60 * 60 * 24)) / (60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / 60;
        //long seconds = (mss % (1000 * 60));
        long seconds = mss - hours * 3600 - minutes * 60;
        return hours + " 小时 " + minutes + " 分 "
                + seconds + " 秒 ";
    }

    @RequestMapping("downLoad")
    public void downLoad(String title, String chapterUrl, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
       /* //文件路径
        File file=new File("D:\\resources\\cmfz_lsm\\src\\main\\webapp"+chapterUrl);
        //修改时候的名字
        String extension = FilenameUtils.getExtension(chapterUrl);
       String oldName= title+"."+extension;
       //设置响应的编码格式
        String encode=null;
        try {
          encode = URLEncoder.encode(oldName,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //设置响应头
        response.setHeader("Content-Disposition","attachment:fileName="+encode);
       *//* ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            outputStream.write(FileUtils.readFileToByteArray(file));
        } catch (IOException e) {
            e.printStackTrace();
        }*//*
        FileInputStream is=new FileInputStream(file);
        OutputStream os=response.getOutputStream();
        while (true){
            int i=is.read();
            if(i==-1){
                break;
            }os.write(i);
        }*/
        InputStream is = new FileInputStream("D:\\resources\\cmfz_lsm\\src\\main\\webapp" + chapterUrl);
//响应头
        String extension = FilenameUtils.getExtension(chapterUrl);
        String oldName = title + "." + extension;
        String s = URLEncoder.encode(oldName, "UTF-8");
        response.setHeader("content-disposition", "attachment;fileName=" + s);
//取
        OutputStream os = response.getOutputStream();
        while (true) {
            int i = is.read();
            if (i == -1) {
                break;
            }
            os.write(i);
        }
    }

    @RequestMapping("play")
    public void play(String title, String chapterUrl, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //文件路径
        File file = new File("D:\\resources\\cmfz_lsm\\src\\main\\webapp" + chapterUrl);
        //修改时候的名字
        String extension = FilenameUtils.getExtension(chapterUrl);
        String oldName = title + "." + extension;
        //设置响应的编码格式
        String encode = null;
        try {
            encode = URLEncoder.encode(oldName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //设置响应头
        response.setHeader("Content-Disposition", "attachment:fileName=" + encode);
       /*ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            outputStream.write(FileUtils.readFileToByteArray(file));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        FileInputStream is = new FileInputStream(file);
        OutputStream os = response.getOutputStream();
        while (true) {
            int i = is.read();
            if (i == -1) {
                break;
            }
            os.write(i);
        }

    }
}
