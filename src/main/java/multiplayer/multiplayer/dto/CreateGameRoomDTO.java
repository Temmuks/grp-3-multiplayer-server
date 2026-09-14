package multiplayer.multiplayer.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class CreateGameRoomDTO {

    private String clientId;
    @Min(value = 2, message = "Min players must be at least 2")
    @Max(value = 15, message = "Max players must be at most 15")
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
