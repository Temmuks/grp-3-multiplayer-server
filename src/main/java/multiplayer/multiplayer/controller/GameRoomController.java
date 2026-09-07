package multiplayer.multiplayer.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import multiplayer.multiplayer.Service.GameService;
import multiplayer.multiplayer.model.GameRoom;
import multiplayer.multiplayer.model.Player;
import org.springframework.web.bind.annotation.RequestBody;

@RestController

@RequestMapping("/api")
@CrossOrigin(origins = { "http://localhost:5173", "http://localhost:5174" })
public class GameRoomController {

    private final GameService gameService;

    public GameRoomController(GameService gameService) {
        this.gameService = gameService;
    }

    // Skapar Gameroom och knyter det till clientens ID
    @PostMapping("/gameRooms")
    public GameRoom createGameRoom(@RequestBody String clientId) {
        GameRoom gameRoom = gameService.createGameRoom();
        gameRoom.setGameRoomOwner(clientId);
        return gameRoom;
    }

    // Ansluter till ett gameroom med en ny spelare
    // Spara spelar Id i clienten som "currentPlayer"
    @PostMapping("/join/{gameRoomId}")
    public String joinGameRoom(@RequestParam("gameRoomId") String gameRoomId) {
        Player player = new Player();
        gameService.addNewPlayer(player, gameRoomId);
        return player.getPlayerId();
    }

}
