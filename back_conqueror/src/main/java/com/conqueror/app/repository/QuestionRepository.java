package com.conqueror.app.repository;

import com.conqueror.app.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Bogdan Kaftanatiy
 */
public interface QuestionRepository extends JpaRepository<Question, Long> {

}
