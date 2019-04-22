package com.baizhi.service.impl;

import com.baizhi.entity.Banner;
import com.baizhi.mapper.BannerMapper;
import com.baizhi.service.BannerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BannerServiceImpl implements BannerService {
    @Autowired
    BannerMapper bannerMapper;

    @Override
    public Map selectAll(Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        PageInfo<Banner> bannerPageInfo = new PageInfo<>(bannerMapper.selectAll());
        Map map = new HashMap();
        map.put("rows", bannerPageInfo.getList());
        map.put("total", bannerPageInfo.getTotal());
        return map;
    }

    @Override
    public void insert(Banner banner) {
        bannerMapper.insert(banner);
    }

    @Override
    public void update(Banner banner) {
        bannerMapper.updateByPrimaryKeySelective(banner);

    }

    @Override
    public void delete(Banner banner) {
        bannerMapper.deleteByPrimaryKey(banner);
    }

    @Override
    public List<Banner> selectExl() {
        return bannerMapper.selectAll();
    }
}
