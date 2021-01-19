package com.house.property.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.house.property.entity.Area;
import com.house.property.entity.HouseCollection;
import com.house.property.mapper.HouseCollectionMapper;
import com.house.property.service.HouseCollectionService;
import com.house.property.service.base.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hang.qi
 * @program: property
 * @description: 收藏service
 * @date 2021-01-07 10:48:36
 */
@Slf4j
@Service
public class HouseCollectionServiceImpl extends BaseServiceImpl<HouseCollection> implements HouseCollectionService {

    @Autowired
    private HouseCollectionMapper houseCollectionMapper;

    @Override
    public HouseCollection getOneByUserIdHouseId(HouseCollection houseCollection) {
        QueryWrapper<HouseCollection> query = new QueryWrapper<>();
        List<HouseCollection> houseCollections = selectListByQuery(houseCollection, query);
        return houseCollections.size()>0?houseCollections.get(0):null;

    }

    @Override
    public int getCountUserCollection(Long houseId) {
        QueryWrapper<HouseCollection> query = new QueryWrapper<>();
        query.lambda().eq(HouseCollection::getHouseId,houseId);
        return houseCollectionMapper.selectCount(query);
    }

}
