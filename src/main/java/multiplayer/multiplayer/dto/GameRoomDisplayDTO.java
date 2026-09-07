package multiplayer.multiplayer.dto;

public record GameRoomDisplayDTO(
        String gameRoomId,
        int playerCount,
        int maxPlayers,
        int gridSize,
        String gameRoomStatus,
    String gameRoomOwner,
    String winner,
    String winnerColor) {
}
