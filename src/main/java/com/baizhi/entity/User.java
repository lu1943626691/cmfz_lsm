package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.fastjson.annotation.JSONField;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "c_user")
public class User {
    @Id
    @KeySql(useGeneratedKeys = true)
    @Excel(name = "编号")
    private Integer id;
    @Excel(name = "用户名")
    private String name;
    private String dharma;
    @Excel(name = "性别")
    private Integer sex;
    @Excel(name = "用户名")
    private String province;
    @Excel(name = "用户名")
    private String city;
    @Excel(name = "用户名")
    private String sign;
    @Excel(name = "用户名")
    private String status;
    @Excel(name = "用户名")
    private String phone;
    @Ignore
    private String passWord;
    @Excel(name = "头像", type = 2, width = 40, height = 30, imageType = 1)
    private String headImg;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:ss:mm")
    @JSONField(format = "yyyy-MM-dd HH:ss:mm")
    @Excel(name = "注册时间", format = "yyyy年mm月dd日 HH时MM分ss秒")
    private Date createDate;
    private String masterId;
    private String salt;


}
