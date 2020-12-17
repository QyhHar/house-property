package com.house.property.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.house.property.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author hang.qi
 * @program: property
 * @description: Base 实现类
 * @date 2020-12-16 18:11:41
 */
public class BaseServiceImpl<T> implements BaseService<T> {

    @Autowired
    private BaseMapper<T> baseDao;
    @Override
    public void save(T o) {
        baseDao.insert(o);
    }

    @Override
    public void update(T o) {
        baseDao.updateById(o);
    }

    @Override
    public void delete(Long id) {
        baseDao.deleteById(id);
    }

    @Override
    public List<T> findAll() {
        return baseDao.selectList(null);
    }

    @Override
    public T getById(Long id) {
        return baseDao.selectById(id);
    }
}
