package com.house.property.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.house.property.entity.User;
import com.house.property.mapper.UserMapper;
import com.house.property.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hang.qi
 * @program: property
 * @description: 用户Service实现类
 * @date 2020-12-16 17:49:51
 */
@Slf4j
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserByUserName(String userName) {
        QueryWrapper<User> query = new QueryWrapper<>();
        query.lambda().eq(User::getUserName,userName);
        List<User> users = userMapper.selectList(query);
        if(users.size()>0) return users.get(0);
        return null;
    }

}
