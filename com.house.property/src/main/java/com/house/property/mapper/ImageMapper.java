package com.house.property.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.house.property.entity.House;
import com.house.property.entity.Image;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ImageMapper extends BaseMapper<Image> {
}
