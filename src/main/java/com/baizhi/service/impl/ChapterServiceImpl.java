package com.baizhi.service.impl;

import com.baizhi.entity.Chapter;
import com.baizhi.mapper.ChapterMapper;
import com.baizhi.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    private ChapterMapper chapterMapper;

    @Override
    public List<Chapter> selectById(Integer albumId) {
        Example example = new Example(Chapter.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("albumId", albumId);
        return chapterMapper.selectByExample(example);
    }

    @Override
    public void insert(Chapter chapter) {

        chapterMapper.insert(chapter);
    }
}
