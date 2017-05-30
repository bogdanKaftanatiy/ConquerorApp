package com.conqueror.app.rest;

import com.conqueror.app.entity.Question;
import com.conqueror.app.service.QuestionService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public String getQuestion(String question) {
        Question questionObj = questionService.findByQuestion(question);

        Gson gson = new Gson();
        return gson.toJson(questionObj);

    }
}
