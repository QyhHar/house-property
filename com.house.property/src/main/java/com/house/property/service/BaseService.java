package com.house.property.service;

import java.util.List;

public interface BaseService<T> {

    void save(T o);

    void update(T o);

    void delete(Long id);

    List<T> findAll();

    public T getById(Long id);

}
