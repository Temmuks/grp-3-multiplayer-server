package multiplayer.multiplayer.dto;

import java.util.List;

public record PlayerUpdateDTO(
                String playerColor,
                List<PositionDTO> positions) {
}
