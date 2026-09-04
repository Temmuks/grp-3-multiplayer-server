package multiplayer.multiplayer.controller;

import org.springframework.stereotype.Controller;

import multiplayer.multiplayer.Service.GameService;
import multiplayer.multiplayer.model.Player;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;

@Controller
public class GameController {
    private SimpMessagingTemplate messsagingTemplate;
    private GameService gameService;

    public GameController(SimpMessagingTemplate messsagingTemplate, GameService gameService) {
        this.messsagingTemplate = messsagingTemplate;
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

        // convert into displayable format (dont send entire gamerooms)

        // send to subscribers of /topic/gamerooms
        messsagingTemplate.convertAndSend("/topic/gamerooms", "här ska finnas list av gamerooms i ngn form av DTO");
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
