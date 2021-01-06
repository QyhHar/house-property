package com.house.property.service;

import com.house.property.entity.House;
import com.house.property.entity.Image;
import com.house.property.service.base.BaseService;

import java.util.List;

public interface ImageService extends BaseService<Image> {
    List<Image> getImageByHouseId(Long HouseId);
}
