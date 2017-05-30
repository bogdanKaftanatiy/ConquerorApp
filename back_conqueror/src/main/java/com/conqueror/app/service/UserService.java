package com.conqueror.app.service;

import com.conqueror.app.entity.User;

import java.util.List;

/**
 * Service for user entity
 * @author Bogdan Kaftanatiy
 */
public interface UserService extends CrudService<User> {
    List<User> findByName(String name);
}
