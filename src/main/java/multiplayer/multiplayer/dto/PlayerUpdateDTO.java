package multiplayer.multiplayer.dto;

import java.util.List;

public record PlayerUpdateDTO(String playerId,
        String playerColor,
        List<PositionDTO> positions) {
}
