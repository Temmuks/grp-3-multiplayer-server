package multiplayer.multiplayer.dto;

import multiplayer.multiplayer.enums.GameState;

public record SetGameRoomStatusDTO(String clientId, String gameRoomId, GameState gameState) {

}
