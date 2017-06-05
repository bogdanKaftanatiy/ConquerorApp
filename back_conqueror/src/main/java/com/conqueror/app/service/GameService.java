package com.conqueror.app.service;

import com.conqueror.app.entity.Game;
import com.conqueror.app.entity.Question;
import com.conqueror.app.entity.QuestionWrapper;
import com.conqueror.app.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
    private final QuestionWrapperService questionWrapperService;

    private List<Game> activeGames = new ArrayList<>();
    private List<Game> registrationGame = new ArrayList<>();

    @Autowired
    public GameService(UserService userService, QuestionService questionService, QuestionWrapperService questionWrapperService) {
        this.userService = userService;
        this.questionService = questionService;
        this.questionWrapperService = questionWrapperService;
    }

    public boolean sendAnswer(long gameId, String userName, String answer) {
        Game game = findGameById(gameId);
        User user = findUserByUsernameAndGame(userName, game);

        if(game != null && user != null) {
            if (game.defendUser != null && game.defendUser.getName().equals(user.getName())) {
                if (game.defendUser.getName().equals(user.getName())) {
                    game.defendUserAnswer = answer;
                    game.gameSynchronizer.updateAnswer();

                    if(game.attackUserAnswer == null) {
                        game.gameSynchronizer.waitAnswers();
                    }

                    //TODO: both users can send right answer
                    return false;
                }
            } else if (game.attackUser.getName().equals(user.getName())) {
                game.attackUserAnswer = answer;
                game.gameSynchronizer.updateAnswer();

                if(game.defendUser != null && game.defendUserAnswer == null) {
                    game.gameSynchronizer.waitAnswers();
                }

                boolean isTerritoryChange = false;
                game.lastChange = new AbstractMap.SimpleEntry<Long, String>(game.currentTerritory, game.defendUser == null ? null : game.defendUser.getName());
                if (game.currentQuestion.getAnswer().equals(answer)) {
                    game.territory.put(game.currentTerritory, (long) game.users.indexOf(user));
                    game.lastChange = new AbstractMap.SimpleEntry<Long, String>(game.currentTerritory, game.attackUser.getName());
                    isTerritoryChange = true;
                }

                game.currentQuestion = null;
                game.attackUser = null;
                game.attackUserAnswer = null;
                game.defendUser = null;
                game.defendUserAnswer = null;
                game.currentTerritory = null;
                game.currentQuestionNumber++;

                game.gameSynchronizer.endMove();
                game.gameSynchronizer.resetSynchronizer();

                return isTerritoryChange;

            }
        }
        return false;
    }

    public QuestionWrapper attackTerritory(long gameId, String userName, long territoryNumber) {
        Game game = findGameById(gameId);
        if (game != null) {
            Question question = questionService.findRandomQuestion();
            game.currentQuestion = question;
            game.attackUser = findUserByUsernameAndGame(userName, game);

            if (game.territory.get(territoryNumber) != -1) {
                game.defendUser = game.users.get(game.territory.get(territoryNumber).intValue());
            } else {
                game.defendUser = null;
            }

            game.currentTerritory = territoryNumber;

            game.gameSynchronizer.updateAttack();

            return questionWrapperService.getQuestionWrapper(question);
        }
        return null;
    }

    public QuestionWrapper checkMove(long gameId, String userName) {
        Game game = findGameById(gameId);

        if (game != null ) {
            if (game.defendUser != null && game.defendUser.getName().equals(userName)) {
                return questionWrapperService.getQuestionWrapper(game.currentQuestion);
            } else {
                game.gameSynchronizer.waitMoveEnd();
            }
        }

        return null;
    }

    public String waitUserAttack(long gameId) {
        Game game = findGameById(gameId);

        game.gameSynchronizer.waitAttack();
        return "User attacks";
    }

    public Game getGame(String userName) {
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

        userGame.gameSynchronizer.addGamer();

        if(userGame.isReady()) {
            activeGames.add(userGame);
            registrationGame.remove(userGame);
            userGame.initGame();
        } else {
            waitGameReady(userGame.getId());
        }

        return userGame;
    }

    public void gameEnd(long gameId, String winner, String looser) {
        Game game = findGameById(gameId);
        User userWin = findUserByUsernameAndGame(winner, game);
        User userLoo = findUserByUsernameAndGame(looser, game);
        if(userWin.getRating() == null)  {
            userWin.setRating(0D);
        }
        if(userLoo.getRating() == null)  {
            userWin.setRating(0D);
        }

        userWin.setRating(userWin.getRating() + 1);
        userLoo.setRating(userLoo.getRating() - 1);
        activeGames.remove(game);
    }


    public void waitGameReady(long gameId) {
        Game game = findGameById(gameId);
        if(game == null) {
            throw new IllegalStateException("No such game");
        }

        game.gameSynchronizer.waitGameReady();
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

    public Map<Long, Long> getGameTerritory(long gameId) {
        Game game = findGameById(gameId);
        return game.territory;
    }

    public Map.Entry<Long, String> getLastChange(long gameId) {
        Game game = findGameById(gameId);
        return game.lastChange;
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
