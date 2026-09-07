package multiplayer.multiplayer.dto;

import multiplayer.multiplayer.model.Player;

public record GameRoomDisplayDTO(
        String gameRoomId,
        int playerCount,
        int maxPlayers,
        int gridSize,
        String gameRoomStatus,
        Player gameRoomOwner) {
}
