package com.baizhi.mapper;

import com.baizhi.entity.User;
import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends Mapper<User> {
    public Integer selectDate(Integer date);

}
