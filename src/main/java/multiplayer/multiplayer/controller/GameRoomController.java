package multiplayer.multiplayer.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import multiplayer.multiplayer.Service.GameService;
import multiplayer.multiplayer.model.GameRoom;
import multiplayer.multiplayer.model.Player;

@RestController

@RequestMapping("/api")
@CrossOrigin(origins = { "http://localhost:5173", "http://localhost:5174" })
public class GameRoomController {

    private final GameService gameService;

    public GameRoomController(GameService gameService) {
        this.gameService = gameService;
    }

    // Skapar ett gameroom och knyter en ny spelare till det rummet, viktigt att vi
    // i clienten knyter den till denna spelare igenom att t.ex spara detta spelar
    // ID i localstorage
    @PostMapping("/gameRooms")
    public GameRoom createGameRoom() {
        Player player = new Player();
        GameRoom gameRoom = gameService.createGameRoom();
        gameRoom.setGameRoomOwner(player);
        return gameRoom;
    }
}
