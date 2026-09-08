package multiplayer.multiplayer.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import multiplayer.multiplayer.enums.GameState;

public class GameRoomUpdateDTO {
    GameState gameRoomStatus;
    List<PlayerUpdateDTO> playerUpdateDTOList = new ArrayList<>();

    public GameRoomUpdateDTO() {
        gameRoomStatus = GameState.NOT_STARTED;
    }

    public GameState getGameRoomStatus() {
        return gameRoomStatus;
    }

    public void setGameRoomStatus(GameState gameRoomStatus) {
        this.gameRoomStatus = gameRoomStatus;
    }

    public List<PlayerUpdateDTO> getPlayerUpdateDTOList() {
        return playerUpdateDTOList;
    }

    public void setPlayerUpdateDTOList(List<PlayerUpdateDTO> playerUpdateDTOList) {
        this.playerUpdateDTOList = playerUpdateDTOList;
    }

}
