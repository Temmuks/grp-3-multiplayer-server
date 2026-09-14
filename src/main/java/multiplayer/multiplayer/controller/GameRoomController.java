package multiplayer.multiplayer.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import multiplayer.multiplayer.Service.GameRoomService;
import multiplayer.multiplayer.Service.GameService;
import multiplayer.multiplayer.dto.CreateGameRoomDTO;
import multiplayer.multiplayer.dto.GameRoomDisplayDTO;
import multiplayer.multiplayer.dto.GameRoomJoinDTO;
import multiplayer.multiplayer.dto.SetGameRoomStatusDTO;
import multiplayer.multiplayer.mapper.GameRoomMapper;
import multiplayer.multiplayer.model.GameRoom;
import multiplayer.multiplayer.model.Player;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController

@RequestMapping("/api")
// @CrossOrigin(origins = { "http://localhost:5173", "http://localhost:5174" })
public class GameRoomController {

    private final GameService gameService;

    private final GameRoomService gameRoomService;

    public GameRoomController(GameService gameService, GameRoomService gameRoomService) {
        this.gameService = gameService;
        this.gameRoomService = gameRoomService;
    }

    // Skapar Gameroom och knyter det till clientens ID
    @PostMapping("/gameRooms")
    public GameRoomDisplayDTO createGameRoom(@Valid @RequestBody CreateGameRoomDTO createGameRoomDTO) {
        GameRoom gameRoom = gameRoomService.createGameRoom(createGameRoomDTO);
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

    public ResponseEntity<GameRoomJoinDTO> joinGameRoom(@PathVariable String gameRoomId, @RequestBody String clientId) {
        Player player = gameService.createPlayer(gameRoomId);
        boolean isOwner = false;
        if (player == null) {
            return ResponseEntity.badRequest().build();
        } else {
            GameRoomDisplayDTO gameRoomDisplayDTO = GameRoomMapper
                    .toDisplayDTO(gameRoomService.getGameRoomById(gameRoomId));
            GameRoomJoinDTO gameRoomJoinDTO = new GameRoomJoinDTO(player.getPlayerId(), isOwner, gameRoomDisplayDTO,
                    player.getColor());
            if (clientId.replaceAll("\"", "").equals(gameRoomService.getGameRoomById(gameRoomId).getGameRoomOwner())) {
                gameRoomJoinDTO.setOwner(true);
                System.out.println("Owner was set to true!");
            }
            return ResponseEntity.ok(gameRoomJoinDTO);
        }
    }

    // Används för postman, kan behövas i framtiden.
    @GetMapping("/gameRooms")
    public List<GameRoom> getMethodName() {
        return gameRoomService.getAllGameRooms();
    }

    @PatchMapping("/gameroom/start")
    public GameRoom startGameRoom(@RequestBody SetGameRoomStatusDTO setGameRoomStatusDTO) {
        return gameRoomService.startGameRoom(setGameRoomStatusDTO);
    }

    @DeleteMapping("/gameRooms")
    public ResponseEntity<Void> deleteGameRooms() {
        gameRoomService.deleteAllGameRooms();
        return ResponseEntity.ok().build();
    }

}
