package com.baizhi.entity;

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

    private String id;
    private String title;
    private String size;
    private String duration;
    private Integer albumId;
    private String chapterUrl;

}
