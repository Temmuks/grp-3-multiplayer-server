package multiplayer.multiplayer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;

@Controller
public class GameController {
    private SimpMessagingTemplate messsagingTemplate;

    public GameController(SimpMessagingTemplate messsagingTemplate) {
        this.messsagingTemplate = messsagingTemplate;
    }

    // Gameloop som med en satt intervall updaterar klienten med nya positioner
    @Scheduled(fixedRate = 60)
    public void gameTick() {

        // gameService.tick();

        // messagingTemplate.convertAndSend("/topic/game/{gameRoomId}")

    }

    // Hanterar logik för att svänga med sin mask
    @MessageMapping("/turn")
    public void turn(String playerId, String direction) {
        // gameService.updatePlayerDirection(playerid, driection)
    }

}
