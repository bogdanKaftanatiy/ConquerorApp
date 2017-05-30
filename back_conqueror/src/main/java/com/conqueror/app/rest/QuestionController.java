package com.conqueror.app.rest;

import com.conqueror.app.entity.Question;
import com.conqueror.app.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Bogdan Kaftanatiy
 */
@RestController
@RequestMapping(value = "rest/question")
public class QuestionController {
    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String createQuestion(@RequestBody Question question) {
        questionService.save(question);
        return "Save question: " + question.toString();
    }
}
