package com.conqueror.app.rest;

import com.conqueror.app.entity.User;
import com.conqueror.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest repository for user entity
 * @author Bogdan Kaftanatiy
 */
@RestController
@RequestMapping(value = "rest/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String createUser(@RequestBody User user) {
        userService.save(user);
        return "Save user: " + user.toString();
    }

    @GetMapping("/check")
    public boolean checkUser(String username, String password) {
        User user = userService.findByName(username);
        if (user != null && user.getPassword().equals(password)){
            return true;
        }

        return false;
    }
}
