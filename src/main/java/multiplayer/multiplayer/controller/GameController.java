package multiplayer.multiplayer.controller;

import org.springframework.stereotype.Controller;

import multiplayer.multiplayer.mapper.GameRoomMapper;
import multiplayer.multiplayer.Service.GameService;
import multiplayer.multiplayer.dto.DashDTO;
import multiplayer.multiplayer.dto.GameRoomDisplayDTO;
import multiplayer.multiplayer.dto.GameRoomUpdateDTO;
import multiplayer.multiplayer.dto.TurnDTO;
import multiplayer.multiplayer.enums.GameState;
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

        gameService.getAllGameRooms().forEach(gameRoom -> {
            if (gameRoom.getGameRoomStatus().equals(GameState.IN_PROGRESS)) {
                GameRoomUpdateDTO gameRoomUpdateDTO = gameService.tick(gameRoom.getGameRoomId());
                messagingTemplate.convertAndSend("/topic/game/" + gameRoom.getGameRoomId(), gameRoomUpdateDTO);
            }
            // System.out.println(gameRoomUpdateDTO);
        });

    }

    // Todo: ta bort Scheduled och använd sendTo här istället
    @Scheduled(fixedRate = 500)
    public void broadcastGameRoomList() {
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
    public void turn(TurnDTO turnDTO) {
        gameService.updatePlayerDirection(turnDTO);
    }

    @MessageMapping("/dash")
    public void dash(DashDTO dashDTO){
        gameService.activateDash(dashDTO);
    }

}
