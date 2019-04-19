package com.baizhi.service;

import com.baizhi.entity.Chapter;

import java.util.List;

public interface ChapterService {
    public List<Chapter> selectById(Integer albumId);

    public void insert(Chapter chapter);
}
