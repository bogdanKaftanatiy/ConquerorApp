package com.conqueror.app.service;

import com.conqueror.app.entity.Question;
import com.conqueror.app.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

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

    @Override
    public List<Question> getQuestions(int count) {
        //TODO create new list with 'count' different questions
        return null;
    }

    @Override
    public Question findRandomQuestion() {
        List<Question> questions = findAll();

        return questions.get(new Random().nextInt(questions.size()));
    }

    @Override
    public Question findByQuestion(String question) {
        return questionRepository.findByQuestion(question);
    }
}
