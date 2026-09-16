package multiplayer.multiplayer.dto;

public class GameRoomJoinDTO {
    int maxPlayers;
    String playerId;
    boolean isOwner;
    GameRoomDisplayDTO gameRoomDisplayDTO;
    String playerColor;

    public GameRoomJoinDTO(int maxPlayers, String playerId, boolean isOwner, GameRoomDisplayDTO gameRoomDisplayDTO,
            String playerColor) {
        this.maxPlayers = maxPlayers;
        this.playerId = playerId;
        this.isOwner = isOwner;
        this.gameRoomDisplayDTO = gameRoomDisplayDTO;
        this.playerColor = playerColor;
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

    public String getPlayerColor() {
        return playerColor;
    }

    public void setPlayerColor(String playerColor) {
        this.playerColor = playerColor;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

}
