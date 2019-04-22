package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "c_album")
public class Album {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    @Excel(name = "专辑名称", needMerge = true)
    private String title;
    private Integer amount = null;
    @Excel(name = "专辑封面", type = 2, width = 40, height = 40, imageType = 1, needMerge = true)
    private String imgPath;
    private Integer score;
    private String author;
    private String boardCast;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:ss:mm")
    @JSONField(format = "yyyy-MM-dd HH:ss:mm")
    @Excel(name = "出版时间", format = "yyyy年MM月dd日", width = 50, needMerge = true)
    private Date publishDate;
    private String brief;
    @ExcelCollection(name = "章节列表")
    private List<Chapter> children;


}
