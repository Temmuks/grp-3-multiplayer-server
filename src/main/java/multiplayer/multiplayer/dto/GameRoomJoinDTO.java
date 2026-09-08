package multiplayer.multiplayer.dto;

public class GameRoomJoinDTO {
    String playerId;
    GameRoomDisplayDTO gameRoomDisplayDTO;

    public GameRoomJoinDTO(String playerId, GameRoomDisplayDTO gameRoomDisplayDTO) {
        this.playerId = playerId;
        this.gameRoomDisplayDTO = gameRoomDisplayDTO;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public GameRoomDisplayDTO getGameRoomDisplayDTO() {
        return gameRoomDisplayDTO;
    }

    public void setGameRoomDisplayDTO(GameRoomDisplayDTO gameRoomDisplayDTO) {
        this.gameRoomDisplayDTO = gameRoomDisplayDTO;
    }

}
