package com.baizhi.service;

import com.baizhi.entity.Album;

import java.util.List;

public interface AlbumService {
    public List<Album> selectAll();

    public Album selectById(Integer id);

    public void insert(Album album);
}
