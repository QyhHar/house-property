package com.house.property.service;

import com.house.property.entity.User;

public interface UserService extends BaseService<User> {
    User getUserByUserName(String userName);
}
