package com.house.property.service;

import com.house.property.entity.User;
import com.house.property.service.base.BaseService;

public interface UserService  extends BaseService<User> {
    User getUserByUserName(String userName);
}
