package multiplayer.multiplayer.mapper;
import multiplayer.multiplayer.dto.GameRoomDisplayDTO;
import multiplayer.multiplayer.model.GameRoom;
import multiplayer.multiplayer.model.Player;

public class GameRoomMapper {
    public static GameRoomDisplayDTO toDisplayDTO(GameRoom gameRoom) {
        // winner är playerId för vinnaren (om matchen är klar).
        String winner = gameRoom.getWinner();
        String winnerColor = null;

        // Kollart upp vinnarens färg via players-map med winner-id.
        if (winner != null) {
            Player winnerPlayer = gameRoom.getPlayers().get(winner);
            if (winnerPlayer != null) {
                winnerColor = winnerPlayer.getColor();
            }
        }
        GameRoomDisplayDTO dto = new GameRoomDisplayDTO(
                gameRoom.getGameRoomId(),
                gameRoom.getPlayers().size(),
                gameRoom.getMaxPlayers(),
                gameRoom.getGridSize(),
                gameRoom.getGameRoomStatus(),
                gameRoom.getGameRoomOwner(),
                winner,
                winnerColor);

        return dto;
    }
}
