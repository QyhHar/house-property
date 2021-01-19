package com.house.property.service.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

public interface BaseService<T> {

    void save(T o);

    void update(T o);

    void delete(Long id);

    void delete(List<Long> ids);

    void delete(UpdateWrapper<T> selectWrapper);

    List<T> findAll();

    T getById(Long id);

    IPage<T> selectPageByCondition(IPage<T> page , QueryWrapper<T> selectWrapper);

    List<T> selectListByQuery(T T, QueryWrapper<T> wrapper);

    IPage<T> selectPageByQuery(IPage<T> page ,T T, QueryWrapper<T> wrapper);

}
