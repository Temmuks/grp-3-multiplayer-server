package multiplayer.multiplayer.dto;

public record PlayerUpdateDTO(String playerId,
        String playerColor,
        PositionChangeDTO positionChangeDTO) {
}
