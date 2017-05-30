package com.conqueror.app.repository;

import com.conqueror.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for user entity
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByName(String name);
}
