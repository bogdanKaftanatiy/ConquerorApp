package com.conqueror.app.entity;

import com.conqueror.app.service.GameService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bogdan Kaftanatiy
 */
public class Game {
    private long id;

    private List<User> users;

    private List<Question> questions;

    private List<Long> usersOrder;

    private List<Long> userCastleLocation;

    public Game(long id) {
        this.id = id;
        users = new ArrayList<>(GameService.USER_COUNT);
        questions = new ArrayList<>(GameService.QUESTION_COUNT);
        usersOrder = new ArrayList<>(GameService.QUESTION_COUNT);
        userCastleLocation = new ArrayList<>(GameService.USER_COUNT);
    }

    public void initGame(List<Question> questionList) {
        calculateUsersCastleLocation();
        calculateUsersOrder();
        fillQuestionList(questionList);
    }

    private void calculateUsersCastleLocation() {
        userCastleLocation.add(2L);
        userCastleLocation.add(7L);
        userCastleLocation.add(11L);
    }

    private void calculateUsersOrder() {
        for (int i = 0; i < GameService.MOVE_COUNT; i++) {
            usersOrder.add(0L);
            usersOrder.add(1L);
            usersOrder.add(2L);
        }
    }

    private void fillQuestionList(List<Question> questionList) {
        if(questionList != null && !questionList.isEmpty()) {
            questions.addAll(questionList);
        }
    }

    public synchronized boolean addUser(User user) {
        if(users.size() == 3) {
            return false;
        }
        users.add(user);
        return true;
    }

    public boolean isReady() {
        return users.size() == 3;
    }

    public long getId() {
        return id;
    }

    public List<Long> getUsersOrder() {
        return usersOrder;
    }

    public List<Long> getUserCastleLocation() {
        return userCastleLocation;
    }
}
