package com.conqueror.app.service;

import com.conqueror.app.entity.User;

/**
 * Service for user entity
 * @author Bogdan Kaftanatiy
 */
public interface UserService extends CrudService<User> {
    User findByName(String name);
}
