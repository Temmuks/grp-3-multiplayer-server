package multiplayer.multiplayer.dto;

import multiplayer.multiplayer.enums.GameState;

public record GameRoomDisplayDTO(
        String gameRoomId,
        int playerCount,
        int maxPlayers,
        int gridSize,
        GameState gameRoomStatus,
        String winner,
        String winnerColor) {
}
