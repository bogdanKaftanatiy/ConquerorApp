package com.conqueror.app.service;

import com.conqueror.app.entity.Option;
import com.conqueror.app.entity.Question;
import com.conqueror.app.entity.QuestionWrapper;
import com.conqueror.app.repository.OptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Bogdan Kaftanatiy
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class QuestionWrapperService {
    private final int QUESTION_COUNT = 4;

    private final OptionRepository optionRepository;

    @Autowired
    public QuestionWrapperService(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }

    public QuestionWrapper getQuestionWrapper(Question question) {
        QuestionWrapper questionWrapper = new QuestionWrapper();
        questionWrapper.setQuestion(question.getQuestion());

        int rightAnswerIndex = ThreadLocalRandom.current().nextInt(QUESTION_COUNT);
        questionWrapper.setAnswer(rightAnswerIndex, question.getAnswer());

        List<Option> allOptions = optionRepository.findByCategory(question.getCategory());

        int startIndex = 0;
        int offset = allOptions.size()/3;
        for (int i = 0; i < QUESTION_COUNT - 1; i++) {
            int endIndex = i == 3 ? allOptions.size() : startIndex + offset;
            int optionIndex = ThreadLocalRandom.current().nextInt(startIndex, endIndex);
            questionWrapper.addAnswer(allOptions.get(optionIndex).getData());
            startIndex += offset;
        }

        return questionWrapper;
    }

    public QuestionWrapper getEmptyWrapper() {
        QuestionWrapper questionWrapper = new QuestionWrapper();
        questionWrapper.setQuestion("");

        return questionWrapper;
    }
}
