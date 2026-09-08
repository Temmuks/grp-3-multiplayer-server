package multiplayer.multiplayer.dto;

public class CreateGameRoomDTO {

    private String clientId;
    private int maxPlayers;

    public CreateGameRoomDTO() {
    }

    public CreateGameRoomDTO(String clientId, int maxPlayers) {
        this.clientId = clientId;
        this.maxPlayers = maxPlayers;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }
    
}
