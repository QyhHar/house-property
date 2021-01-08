package com.house.property.service;

import com.house.property.entity.HouseCollection;
import com.house.property.entity.User;
import com.house.property.service.base.BaseService;

public interface HouseCollectionService extends BaseService<HouseCollection> {

    HouseCollection getOneByUserIdHouseId(HouseCollection houseCollection);

}
