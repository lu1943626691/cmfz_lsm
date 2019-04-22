package com.baizhi.service.impl;

import com.baizhi.entity.China;
import com.baizhi.mapper.ChinaMapper;
import com.baizhi.service.ChinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChinaServiceImpl implements ChinaService {
    @Autowired
    private ChinaMapper chinaMapper;

    @Override
    public List<China> selectBySex(Integer sex) {
        return chinaMapper.selectBySex(sex);
    }
}
