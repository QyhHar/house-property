package com.house.property.service;

import com.house.property.entity.Area;
import com.house.property.entity.House;
import com.house.property.service.base.BaseService;
import com.house.property.utils.TreeNode;

import java.util.List;

public interface HouseService extends BaseService<House> {
    List<House> getByUserId(Long userId);
}
