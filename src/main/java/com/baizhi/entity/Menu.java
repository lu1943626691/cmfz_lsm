package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "c_menu")
public class Menu implements Serializable {
    private Integer id;
    private String title;
    private String icon_cls;
    private Integer parent_id;
    private String jsp_url;
    private List<Menu> list;
}
