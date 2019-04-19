package com.baizhi.entity;

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
    private String title;
    private Integer amount = null;
    private String imgPath;
    private Integer score;
    private String author;
    private String boardCast;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:ss:mm")
    @JSONField(format = "yyyy-MM-dd HH:ss:mm")
    private Date publishDate;
    private String brief;
    private List<Chapter> children;


}
