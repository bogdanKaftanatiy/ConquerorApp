package com.conqueror.app.service;

import com.conqueror.app.entity.Question;

import java.util.List;

/**
 * @author Bogdan Kaftanatiy
 */
public interface QuestionService extends CrudService<Question> {
    List<Question> getQuestions(int count);

    Question findByQuestion(String question);
}
