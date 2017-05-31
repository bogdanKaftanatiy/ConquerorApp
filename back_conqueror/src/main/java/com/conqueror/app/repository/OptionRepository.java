package com.conqueror.app.repository;

import com.conqueror.app.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Bogdan Kaftanatiy
 */
public interface OptionRepository extends JpaRepository<Option, Long> {
    List<Option> findByCategory(String category);
}
