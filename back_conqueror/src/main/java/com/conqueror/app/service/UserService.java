package com.conqueror.app.service;

import com.conqueror.app.entity.User;

import java.util.List;

/**
 * Service for user entity
 * @author Bogdan Kaftanatiy
 */
public interface UserService {
    List<User> findAll();

    void save(User user);

    User findOne(long id);

    void delete(long id);

    List<User> findByName(String name);
}
