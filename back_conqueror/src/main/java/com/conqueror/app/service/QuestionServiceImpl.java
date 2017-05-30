package com.conqueror.app.service;

import com.conqueror.app.entity.Question;
import com.conqueror.app.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Bogdan Kaftanatiy
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    @Override
    public void save(Question question) {
        questionRepository.save(question);
    }

    @Override
    public Question findOne(long id) {
        return questionRepository.findOne(id);
    }

    @Override
    public void delete(long id) {
        questionRepository.delete(id);
    }
}
