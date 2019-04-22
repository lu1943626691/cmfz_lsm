package com.baizhi.mapper;

import com.baizhi.entity.China;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ChinaMapper extends Mapper<China> {
    public List<China> selectBySex(Integer sex);
}
