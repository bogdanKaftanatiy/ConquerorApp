package com.conqueror.app.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest service for testing Spring Boot
 * @author Bogdan Kaftanatiy
 */
@RestController
@RequestMapping("/test")
public class TestRestService {

    @GetMapping("/hello")
    public String sayHi(String name) {
        return "Hi, " + name + ", from Spring Boot project";
    }
}
