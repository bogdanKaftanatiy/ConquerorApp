package com.conqueror.app.service;

import com.conqueror.app.entity.Question;

/**
 * @author Bogdan Kaftanatiy
 */
public interface QuestionService extends CrudService<Question> {
    Question findByQuestion(String question);
}
