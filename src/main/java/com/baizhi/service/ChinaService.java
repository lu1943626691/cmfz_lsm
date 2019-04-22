package com.baizhi.service;

import com.baizhi.entity.China;

import java.util.List;

public interface ChinaService {
    public List<China> selectBySex(Integer sex);
}
