package com.house.property.service.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

public interface BaseService<T> {

    void save(T o);

    void update(T o);

    void delete(Long id);

    List<T> findAll();

    T getById(Long id);

    IPage<T> selectPageByCondition(IPage<T> page , QueryWrapper<T> selectWrapper);

}
