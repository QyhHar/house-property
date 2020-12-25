package com.house.property.service.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.house.property.service.base.BaseService;
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

    @Override
    public IPage<T> selectPageByCondition(IPage<T> page, QueryWrapper<T> selectWrapper) {
        //说明：根据Example条件进行查询
        //重点：这个查询支持通过Example类指定查询列，通过selectProperties方法指定查询列
        return baseDao.selectPage(page, selectWrapper);
    }
}
