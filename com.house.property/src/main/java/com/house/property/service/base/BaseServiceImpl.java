package com.house.property.service.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.house.property.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public void delete(List<Long> ids) {
        baseDao.deleteBatchIds(ids);
    }

    @Override
    public void delete(UpdateWrapper<T> selectWrapper) {
        baseDao.delete(selectWrapper);
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

    @Override
    public List<T> selectListByQuery(T T, QueryWrapper<T> wrapper) {
        notNullField(T,wrapper);
        return baseDao.selectList(wrapper);
    }

    @Override
    public IPage<T> selectPageByQuery(IPage<T> page ,T T, QueryWrapper<T> wrapper) {
        notNullField(T,wrapper);
        return baseDao.selectPage(page,wrapper);
    }

    public static<T> void notNullField(T T, QueryWrapper<T> wrapper){
        for (Field field : T.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                //序列化 字段不需要查询
                if("serialVersionUID".equals(field.getName())){
                    continue;
                }
                //属性为空，不用查询
                if(field.get(T) == null){
                    continue;
                }
                //主键 注解TableId
                TableId tableId = field.getAnnotation(TableId.class);
                if (tableId != null){
                    //主键
                    wrapper.eq(tableId.value(),field.get(T));
                    continue;
                }
                //数据库中字段名和实体类属性不一致 注解TableField
                TableField tableField = field.getAnnotation(TableField.class);
                if(tableField != null){
                    if(tableField.exist()){
                        wrapper.eq(tableField.value(),field.get(T));
                    }// @TableField(exist = false) 不是表中内容 不形成查询条件
                    continue;
                }
                //范围型参数
                if(field.getType() == String.class&&String.valueOf(field.get(T)).contains("-")){
                    String[] value = String.valueOf(field.get(T)).split("-");
                    if(value.length==2&&value[0].matches("\\d+")&&value[1].matches("\\d+")){
                        wrapper.between(humpToLine(field.getName()),value[0],value[1]);
                        continue;
                    }
                }
                //数据库中字段名和实体类属性一致
                wrapper.eq(humpToLine(field.getName()),field.get(T));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
    private static Pattern humpPattern = Pattern.compile("[A-Z]");
    public static String humpToLine(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}
