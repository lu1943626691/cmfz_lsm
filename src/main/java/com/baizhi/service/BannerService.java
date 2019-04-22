package com.baizhi.service;

import com.baizhi.entity.Banner;

import java.util.List;
import java.util.Map;

public interface BannerService {
    public Map selectAll(Integer page, Integer rows);

    public void insert(Banner banner);

    public void update(Banner banner);

    public void delete(Banner banner);

    List<Banner> selectExl();
}
