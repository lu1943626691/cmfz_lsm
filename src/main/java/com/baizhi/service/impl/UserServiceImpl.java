package com.baizhi.service.impl;

import com.baizhi.entity.User;
import com.baizhi.mapper.UserMapper;
import com.baizhi.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public Map selectAll(Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        PageInfo<User> userPageInfo = new PageInfo<>(userMapper.selectAll());
        Map map = new HashMap();
        map.put("rows", userPageInfo.getList());
        map.put("total", userPageInfo.getTotal());
        return map;
    }

    @Override
    public void insert(User user) {
        userMapper.insert(user);

    }

    @Override
    public void delete(User user) {
        userMapper.deleteByPrimaryKey(user);
    }

    @Override
    public void update(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public List<User> select() {
        return userMapper.selectAll();
    }

    @Override
    public Integer selectDate(Integer date) {
        return userMapper.selectDate(date);
    }
}
