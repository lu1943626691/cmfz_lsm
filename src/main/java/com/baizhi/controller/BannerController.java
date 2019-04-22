package com.baizhi.controller;

import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

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

    @RequestMapping("selectExl")
    public void selectAll(HttpServletResponse res) throws Exception {
        List<Banner> bannerList = bannerService.selectExl();
        //1.创建工作薄对象
        HSSFWorkbook workbook = new HSSFWorkbook();
        //2.创建工作表
        HSSFSheet bannerSheet = workbook.createSheet("Banner");
        bannerSheet.setColumnWidth(2, 20 * 256);
        //3.创建行,创建第几行
        HSSFRow row = bannerSheet.createRow(0);
        //第一行字段
        String[] titles = {"编号", "图片名称", "创建时间", "状态"};
        //创建单元格对象
        HSSFCell cell = null;
        for (int i = 0; i < titles.length; i++) {
            //i 标示列索引
            cell = row.createCell(i);
            cell.setCellValue(titles[i]);
        }
        //处理数据行
        for (int i = 0; i < bannerList.size(); i++) {
            Row row1 = bannerSheet.createRow(i + 1);
            row1.createCell(0).setCellValue(bannerList.get(i).getId());
            row1.createCell(1).setCellValue(bannerList.get(i).getTitle());
            Cell cell2 = row1.createCell(2);
            cell2.setCellValue(bannerList.get(i).getCreate_date());
            HSSFDataFormat dataFormat = workbook.createDataFormat();
            short format = dataFormat.getFormat("yyyy年MM月dd日");
            CellStyle cellStyle1 = workbook.createCellStyle();
            cellStyle1.setDataFormat(format);

            cell2.setCellStyle(cellStyle1);

            //row1.createCell(2).setCellValue(bannerList.get(i).getCreate_date());

            row1.createCell(3).setCellValue(bannerList.get(i).getStatus());
            //日期
            /*HSSFCell cell=row.createCell(0);
            cell.setCellValue(new Date());

            style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
            cell.setCellStyle(style);*/
        }
        /* Map map=new HashMap();*/
        //将excel的数据写入文件
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
            res.setHeader("Content-Disposition", "attachment; filename=banner.xls");//要保存的文件名
            res.setContentType("application/octet-stream; charset=utf-8");
            os.write(retArr);
            os.flush();
        } finally {
            if (os != null) {
                os.close();
            }
        }
       /* try {
            workbook.write(new File("d:\\test.xls"));
            workbook.close();
            map.put("isOk","true");

        } catch (IOException e) {
            e.printStackTrace();
            map.put("isOk","false");
        }
        return map;
*/


    }
}
