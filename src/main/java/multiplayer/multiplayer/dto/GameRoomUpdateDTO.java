package multiplayer.multiplayer.dto;

import java.util.HashMap;
import java.util.Map;

import multiplayer.multiplayer.enums.GameState;

public class GameRoomUpdateDTO {
    Map<PositionDTO, String> playerPositions;
    GameState gameRoomStatus;

    public GameRoomUpdateDTO(){
        gameRoomStatus = GameState.NOT_STARTED;
        playerPositions = new HashMap<>();
    }

    public Map<PositionDTO, String> getPlayerPositions() {
        return playerPositions;
    }

    public void setPlayerPositions(Map<PositionDTO, String> playerPositions) {
        this.playerPositions = playerPositions;
    }

    public GameState getGameRoomStatus() {
        return gameRoomStatus;
    }

    public void setGameRoomStatus(GameState gameRoomStatus) {
        this.gameRoomStatus = gameRoomStatus;
    }



}
