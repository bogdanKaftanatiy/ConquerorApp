package com.conqueror.app.rest;

import com.conqueror.app.entity.Game;
import com.conqueror.app.service.GameService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Bogdan Kaftanatiy
 */
@RestController
@RequestMapping("/rest/game")
public class GameController {
    private final GameService gameService;
    private final Gson gson = new Gson();

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("getGame")
    public Long getGameForUser(String username) {
        Game game = gameService.getGame(username);
        return game.getId();
    }


    @GetMapping("usersOrder")
    public String getUsersOrder(long gameId) {
        return gson.toJson(gameService.getUsersOrder(gameId));
    }

    @GetMapping("usersCastleLocation")
    public String getUsersCastleLocation(long gameId) {
        return gson.toJson(gameService.getUsersCastleLocation(gameId));
    }

    @GetMapping("attackTerritory")
    public String attackTerritory(long gameId, String username, long territoryNumber) {
        return gson.toJson(gameService.attackTerritory(gameId, username, territoryNumber));
    }
}
