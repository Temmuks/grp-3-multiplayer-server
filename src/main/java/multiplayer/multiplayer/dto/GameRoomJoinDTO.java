package multiplayer.multiplayer.dto;

public class GameRoomJoinDTO {
    String playerId;
    boolean isOwner;
    GameRoomDisplayDTO gameRoomDisplayDTO;

    public GameRoomJoinDTO(String playerId, boolean isOwner, GameRoomDisplayDTO gameRoomDisplayDTO) {
        this.playerId = playerId;
        this.isOwner = isOwner;
        this.gameRoomDisplayDTO = gameRoomDisplayDTO;
    }

    public GameRoomJoinDTO() {
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

    public boolean isOwner() {
        return isOwner;
    }

    public void setOwner(boolean isOwner) {
        this.isOwner = isOwner;
    }

}
