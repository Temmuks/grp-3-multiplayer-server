package multiplayer.multiplayer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import multiplayer.multiplayer.Service.GameService;
import multiplayer.multiplayer.model.GameRoom;

@Controller

@RequestMapping("/api")
public class GameRoomController {

    private final GameService gameService;

    public GameRoomController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/gameRooms")
    public GameRoom createGameRoom() {
        return gameService.createGameRoom();
    }
}


