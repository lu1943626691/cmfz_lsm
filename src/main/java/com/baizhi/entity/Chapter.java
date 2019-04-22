package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "c_chapter")
public class Chapter {
    @Id
    @Excel(name = "编号")
    private String id;
    @Excel(name = "章节名称")
    private String title;
    @Excel(name = "章节大小")
    private String size;
    @Excel(name = "章节时长")
    private String duration;
    @ExcelIgnore
    private Integer albumId;
    @ExcelIgnore
    private String chapterUrl;

}
