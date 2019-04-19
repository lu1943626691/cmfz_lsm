package com.baizhi.service.impl;

import com.baizhi.entity.Album;
import com.baizhi.mapper.AlbumMapper;
import com.baizhi.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    private AlbumMapper albumMapper;

    @Override
    public List<Album> selectAll() {
        return albumMapper.selectAll();
    }

    @Override
    public Album selectById(Integer id) {
        return albumMapper.selectByPrimaryKey(id);
    }

    @Override
    public void insert(Album album) {
        albumMapper.insert(album);
    }
}
