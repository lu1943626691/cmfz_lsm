package com.baizhi.service;

import com.baizhi.entity.User;

import java.util.List;
import java.util.Map;


public interface UserService {
    public Map selectAll(Integer page, Integer rows);

    public void insert(User user);

    void delete(User user);

    void update(User user);

    List<User> select();

    Integer selectDate(Integer date);
}
