package multiplayer.mapper;

import multiplayer.multiplayer.dto.GameRoomDisplayDTO;
import multiplayer.multiplayer.model.GameRoom;

public class GameRoomMapper {
    public static GameRoomDisplayDTO toDisplayDTO(GameRoom gameRoom) {
        GameRoomDisplayDTO dto = new GameRoomDisplayDTO(
            gameRoom.getGameRoomId(),
            gameRoom.getPlayers().size(),
            gameRoom.getMaxPlayers(),
            gameRoom.getGridSize(),
            gameRoom.getGameRoomStatus()
        );
        
        return dto;
    }    
}
