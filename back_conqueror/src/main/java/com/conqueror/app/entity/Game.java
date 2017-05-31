package com.conqueror.app.entity;

import com.conqueror.app.service.GameService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Bogdan Kaftanatiy
 */
public class Game {
    private long id;

    public List<User> users;
    public List<String> usersOrder;
    public List<Long> userCastleLocation;
    public Map<Long, Long> territory;

    public long curentQuestionNumber = 0;
    public Question currentQuestion;
    public String attackUserAnswer;
    public String defendUserAnswer;
    public User attackuser;
    public User defendUser;

    public Game(long id) {
        this.id = id;
        users = new ArrayList<>(GameService.USER_COUNT);
        usersOrder = new ArrayList<>(GameService.QUESTION_COUNT);
        userCastleLocation = new ArrayList<>(GameService.USER_COUNT);
        territory = new HashMap<>();
    }

    public void initGame() {
        calculateUsersCastleLocation();
        calculateUsersOrder();
        initMap();
    }

    private void calculateUsersCastleLocation() {
        userCastleLocation.add(2L);
        userCastleLocation.add(7L);
        userCastleLocation.add(11L);
    }

    private void calculateUsersOrder() {
        for (int i = 0; i < GameService.MOVE_COUNT; i++) {
            usersOrder.add(users.get(0).getName());
            usersOrder.add(users.get(1).getName());
            usersOrder.add(users.get(2).getName());
        }
    }

    private void initMap() {
        for (long i = 1; i <= 15; i++){
            territory.put(i, -1L);
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
}