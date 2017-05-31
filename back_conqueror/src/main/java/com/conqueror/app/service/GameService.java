package com.conqueror.app.service;

import com.conqueror.app.entity.Game;
import com.conqueror.app.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bogdan Kaftanatiy
 */
@Service
public class GameService {
    public static final int USER_COUNT = 3;
    public static final int MOVE_COUNT = 7;
    public static final int QUESTION_COUNT = USER_COUNT * MOVE_COUNT;

    private long idGenerator = 0;

    private final UserService userService;
    private final QuestionService questionService;

    List<Game> activeGames = new ArrayList<>();
    List<Game> registrationGame = new ArrayList<>();

    @Autowired
    public GameService(UserService userService, QuestionService questionService) {
        this.userService = userService;
        this.questionService = questionService;
    }

    public synchronized Game getGame(String userName) {
        User user = userService.findByName(userName);
        if(user == null) {
            return null;
        }

        Game userGame;
        if(registrationGame.isEmpty()) {
            userGame = new Game(idGenerator++);
            registrationGame.add(userGame);
        } else {
            userGame = registrationGame.get(0);
        }

        if(!userGame.addUser(user)) {
            throw new IllegalStateException("Game is already full");
        }

        if(userGame.isReady()) {
            activeGames.add(userGame);
            registrationGame.remove(userGame);
            userGame.initGame(questionService.getQuestions(QUESTION_COUNT));
            notifyAll();
        } else {
            waitGameReady(userGame.getId());
        }

        return userGame;
    }


    public synchronized void waitGameReady(long gameId) {
        Game game = findGameById(gameId);
        if(game == null) {
            throw new IllegalStateException("No such game");
        }

        while (!game.isReady()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Long> getUsersOrder(long gameId) {
        Game game = findGameById(gameId);

        if (game == null) {
            throw new IllegalStateException("No such game");
        }

        return game.getUsersOrder();
    }

    public List<Long> getUsersCastleLocation(long gameId) {
        Game game = findGameById(gameId);

        if (game == null) {
            throw new IllegalStateException("No such game");
        }

        return game.getUserCastleLocation();
    }

    private Game findGameById(long id) {
        for (Game regGame : registrationGame) {
            if(regGame.getId() == id) {
                return regGame;
            }
        }
        for (Game actGame : activeGames) {
            if(actGame.getId() == id) {
                return actGame;
            }
        }
        return null;
    }
}
