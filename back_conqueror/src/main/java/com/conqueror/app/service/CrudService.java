package com.conqueror.app.service;


import java.util.List;

/**
 * @author Bogdan Kaftanatiy
 */
public interface CrudService<T> {
    List<T> findAll();

    void save(T object);

    T findOne(long id);

    void delete(long id);
}
