package com.baizhi.service.impl;

import com.baizhi.entity.China;
import com.baizhi.mapper.ChinaMapper;
import com.baizhi.service.ChinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChinaServiceImpl implements ChinaService {
    @Autowired
    private ChinaMapper chinaMapper;

    @Override
    public Map selectBySex(Integer sex) {
        List<China> list = chinaMapper.selectBySex(sex);
        Map map = new HashMap();
        map.put("china", list);
        return map;
    }

}
