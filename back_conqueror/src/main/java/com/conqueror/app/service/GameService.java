package com.conqueror.app.service;

import com.conqueror.app.entity.Game;
import com.conqueror.app.entity.Question;
import com.conqueror.app.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Question attackTerritory(long gameId, String userName, long terittoryNumber) {
        Game game = findGameById(gameId);
        Question question = questionService.findRandomQuestion();
        game.currentQuestion = question;
        game.attackuser = findUserByUsernameAndGame(userName, game);

        if(game.territory.get(terittoryNumber) != -1) {
            game.defendUser = game.users.get(game.territory.get(terittoryNumber).intValue());
        } else {
            game.defendUser = null;
        }

        return question;
    }

    public Question checkMove() {
        return null;
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
            userGame.initGame();
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

    public List<String> getUsersOrder(long gameId) {
        Game game = findGameById(gameId);

        if (game == null) {
            throw new IllegalStateException("No such game");
        }

        return game.usersOrder;
    }

    public Map<String, Long> getUsersCastleLocation(long gameId) {
        Game game = findGameById(gameId);

        if (game == null) {
            throw new IllegalStateException("No such game");
        }

        Map<String, Long> result = new HashMap<>();
        for (int i = 0; i < USER_COUNT; i++) {
            result.put(game.users.get(i).getName(), game.userCastleLocation.get(i));
        }

        return result;
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

    private User findUserByUsernameAndGame(String username, Game game) {
        List<User> gameUsers = game.users;
        for (User user : gameUsers) {
            if (user.getName().equals(username)){
                return user;
            }
        }
        return null;
    }
}
