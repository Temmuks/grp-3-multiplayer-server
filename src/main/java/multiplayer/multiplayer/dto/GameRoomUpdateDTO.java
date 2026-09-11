package multiplayer.multiplayer.dto;

import java.util.ArrayList;
import java.util.List;

import multiplayer.multiplayer.enums.GameState;

public class GameRoomUpdateDTO {
    String winnerColor;
    GameState gameRoomStatus;
    List<PlayerUpdateDTO> playerUpdateDTOList = new ArrayList<>();

    public GameRoomUpdateDTO() {
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

    public String getWinnerColor() {
        return winnerColor;
    }

    public void setWinnerColor(String winnerColor) {
        this.winnerColor = winnerColor;
    }

}
