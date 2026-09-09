package multiplayer.multiplayer.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import multiplayer.multiplayer.Service.GameService;
import multiplayer.multiplayer.dto.CreateGameRoomDTO;
import multiplayer.multiplayer.dto.GameRoomDisplayDTO;
import multiplayer.multiplayer.dto.GameRoomJoinDTO;
import multiplayer.multiplayer.mapper.GameRoomMapper;
import multiplayer.multiplayer.model.GameRoom;
import multiplayer.multiplayer.model.Player;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController

@RequestMapping("/api")
// @CrossOrigin(origins = { "http://localhost:5173", "http://localhost:5174" })
public class GameRoomController {

    private final GameService gameService;

    public GameRoomController(GameService gameService) {
        this.gameService = gameService;
    }

    // Skapar Gameroom och knyter det till clientens ID
    @PostMapping("/gameRooms")
    public GameRoomDisplayDTO createGameRoom(@RequestBody CreateGameRoomDTO createGameRoomDTO) {
        GameRoom gameRoom = gameService.createGameRoom(createGameRoomDTO);
        GameRoomDisplayDTO gameRoomDisplayDTO = GameRoomMapper.toDisplayDTO(gameRoom);
        return gameRoomDisplayDTO;
    }

    // Test method for postman
    // @GetMapping("/status")
    // public GameRoomUpdateDTO getStatus() {
    // GameRoomUpdateDTO gameRoomUpdateDTO = new GameRoomUpdateDTO();
    // gameRoomUpdateDTO =
    // gameService.tick(gameService.getAllGameRooms().get(0).getGameRoomId());
    // return gameRoomUpdateDTO;
    // }

    // Ansluter till ett gameroom med en ny spelare
    // Spara spelar Id i clienten som "currentPlayer"
    @PostMapping("/join/{gameRoomId}")
    public GameRoomJoinDTO joinGameRoom(@PathVariable String gameRoomId) {
        Player player = new Player();
        gameService.createPlayer(gameRoomId);
        GameRoomDisplayDTO gameRoomDisplayDTO = GameRoomMapper.toDisplayDTO(gameService.getGameRoomById(gameRoomId));
        GameRoomJoinDTO gameRoomJoinDTO = new GameRoomJoinDTO(player.getPlayerId(), gameRoomDisplayDTO);
        return gameRoomJoinDTO;
    }

    // Används för postman, kan behövas i framtiden.
    @GetMapping("/gameRooms")
    public List<GameRoom> getMethodName() {
        return gameService.getAllGameRooms();
    }

}
