package com.house.property.service.impl;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.house.property.entity.Area;
import com.house.property.entity.House;
import com.house.property.entity.User;
import com.house.property.mapper.HouseMapper;
import com.house.property.mapper.UserMapper;
import com.house.property.service.HouseService;
import com.house.property.service.UserService;
import com.house.property.service.base.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author hang.qi
 * @program: property
 * @description: 房屋Service实现类
 * @date 2020-12-16 17:49:51
 */
@Slf4j
@Service
public class HouseServiceImpl extends BaseServiceImpl<House> implements HouseService {

    @Autowired
    private HouseMapper houseMapper;


    @Override
    public List<House> getByUserId(Long userId) {
        QueryWrapper<House> query = new QueryWrapper<>();
        query.lambda().eq(House::getUserId,userId);
        return houseMapper.selectList(query);
    }

    @Override
    public IPage<House> getByQuery(House house) {
        QueryWrapper<House> query = new QueryWrapper<>();
        query.lambda().eq(House::getAreaId,house.getAreaId());
        return null;
    }

    public IPage<House> selectPageByMap(IPage<House> page ,Map<String,List<String>> map, QueryWrapper<House> wrapper) {
        getWrapperByMap(map,wrapper);
        return houseMapper.selectPage(page,wrapper);
    }
    public void getWrapperByMap(Map<String,List<String>> map, QueryWrapper<House> wrapper){
        for (Map.Entry<String,List<String>> entry: map.entrySet()) {
            if(entry.getValue().size()>0)
            wrapper.and(wrp->{
                for (int i = 0; i < entry.getValue().size() ; i++) {
                    String value = String.valueOf(entry.getValue().get(i));
                    childWrapper(entry.getKey(), humpToLine(value), wrp);
                    if(i!=entry.getValue().size()-1)wrp.or();
                }
            });
        }
    }
    public void childWrapper(String key,String value,QueryWrapper<House> wrapper){
        if(value.contains("-")){
            String[] str = value.split("-");
            if(str.length==2&&str[0].matches("\\d+")&&str[1].matches("\\d+")){
                wrapper.between(humpToLine(key),str[0],str[1]);
            }
        }else {
            wrapper.eq(humpToLine(key),value);
        }
    }
}
