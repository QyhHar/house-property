package com.house.property.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.house.property.entity.House;
import com.house.property.entity.Image;
import com.house.property.entity.User;
import com.house.property.mapper.HouseMapper;
import com.house.property.mapper.ImageMapper;
import com.house.property.service.HouseService;
import com.house.property.service.ImageService;
import com.house.property.service.base.BaseServiceImpl;
import com.house.property.utils.EnumUtil;
import com.house.property.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hang.qi
 * @program: property
 * @description: 图片Service实现类
 * @date 2020-12-16 17:49:51
 */
@Slf4j
@Service
public class ImageServiceImpl extends BaseServiceImpl<Image> implements ImageService {

    @Autowired
    private ImageMapper imageMapper;

    @Override
    public List<Image> getImageByHouseId(Long HouseId) {
        Object value = RedisCache.getValue(EnumUtil.imageDateCode + HouseId);
        if(value!=null) return (List<Image>)value;
        QueryWrapper<Image> query = new QueryWrapper<>();
        query.lambda().eq(Image::getHouseId,HouseId);
        List<Image> images = imageMapper.selectList(query);
        RedisCache.putValue(EnumUtil.imageDateCode+HouseId,images,10*60);
        return images;
    }
}
