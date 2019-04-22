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
    public Map selectDate() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("intervals", new String[]{"7天", "15天", "30天", "三个月", "半年", "一年"});
        map.put("counts", new int[]{userMapper.selectDate(7), userMapper.selectDate(15), userMapper.selectDate(30), userMapper.selectDate(100), userMapper.selectDate(200), userMapper.selectDate(365)});
        return map;
    }
}
