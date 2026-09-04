package multiplayer.multiplayer.controller;

import org.springframework.stereotype.Controller;

import multiplayer.multiplayer.mapper.GameRoomMapper;
import multiplayer.multiplayer.Service.GameService;
import multiplayer.multiplayer.dto.GameRoomDisplayDTO;
import multiplayer.multiplayer.model.GameRoom;

import java.util.List;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;

@Controller
public class GameController {
    private SimpMessagingTemplate messagingTemplate;
    private GameService gameService;

    public GameController(SimpMessagingTemplate messagingTemplate, GameService gameService) {
        this.messagingTemplate = messagingTemplate;
        this.gameService = gameService;
    }

    // Gameloop som med en satt intervall updaterar klienten med nya positioner
    @Scheduled(fixedRate = 60)
    public void gameTick() {

        // gameService.tick();

        // messagingTemplate.convertAndSend("/topic/game/{gameRoomId}")

    }

    @Scheduled(fixedRate = 500)
    public void broadcastGameRoomList(){
        // get all gamerooms
        List<GameRoom> gameRooms = gameService.getAllGameRooms();

        // convert into displayable format (dont send entire gamerooms)
        List<GameRoomDisplayDTO> DTOs = gameRooms.stream()
            .map(GameRoomMapper::toDisplayDTO)
            .toList();
        
        // send to subscribers of /topic/gamerooms
        messagingTemplate.convertAndSend("/topic/gamerooms", DTOs);
    }

    // Hanterar logik för att svänga med sin mask
    @MessageMapping("/turn")
    public void turn(String playerId, String direction, String gameRoomId) {
        boolean turnSucessful = gameService.updatePlayerDirection(playerId, direction, gameRoomId);
        if (turnSucessful) {
            // turn(player);
        }
    }

}
