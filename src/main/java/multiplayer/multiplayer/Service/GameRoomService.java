package multiplayer.multiplayer.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import multiplayer.multiplayer.dto.CreateGameRoomDTO;
import multiplayer.multiplayer.dto.PositionDTO;
import multiplayer.multiplayer.dto.SetGameRoomStatusDTO;
import multiplayer.multiplayer.enums.GameState;
import multiplayer.multiplayer.model.GameRoom;
import multiplayer.multiplayer.model.Player;

@Service
public class GameRoomService {

    public GameRoomService() {
    }

    public List<GameRoom> gameRoomList = new ArrayList<>();

    // Skapa GameRooms
    public GameRoom createGameRoom(CreateGameRoomDTO createGameRoomDTO) {
        GameRoom gameRoom = new GameRoom();
        gameRoom.setGameRoomStatus(GameState.NOT_STARTED);
        gameRoom.setMaxPlayers(createGameRoomDTO.getMaxPlayers());
        gameRoom.setGameRoomOwner(createGameRoomDTO.getClientId());
        gameRoom.setGridSize(4 * 64);// Sätter gridsize baserat på max antal spelare. 4
                                     // = 256, 10 = 640, 15 =
        // 960 etc.
        String gameRoomId = UUID.randomUUID().toString();
        gameRoom.setGameRoomId(gameRoomId);

        gameRoomList.add(gameRoom);
        return gameRoom;
    }

    // Hämta alla GameRooms

    public List<GameRoom> getAllGameRooms() {
        return gameRoomList;
    }

    public void deleteOwnerRooms(String clientId) {
        gameRoomList.removeIf(gameRoom -> gameRoom.getGameRoomOwner().equals(clientId));
    }

    // Hämta speciofikt GameRoom
    public GameRoom getGameRoomById(String gameRoomId) {
        GameRoom gameRoomById = gameRoomList.stream().filter(gr -> gr.getGameRoomId().equals(gameRoomId)).findFirst()
                .orElseThrow();
        return gameRoomById;
    }

    // Starta GameRoom
    public GameRoom startGameRoom(SetGameRoomStatusDTO setGameRoomStatusDTO) {
        GameRoom gameRoom = getGameRoomById(setGameRoomStatusDTO.gameRoomId());
        if (gameRoom.getGameRoomOwner().equals(setGameRoomStatusDTO.clientId())) {
            distributePlayers(gameRoom.getGameRoomId());
            gameRoom.setGameRoomStatus(setGameRoomStatusDTO.gameState());
        }
        return gameRoom;
    }


    // Radera specifikt GameRoom
    public void deleteGameRoomById(String gameRoomId) {

        gameRoomList.remove(getGameRoomById(gameRoomId));

    }

    // Ge Spelare positioner på en cirkel inom spelarean
    public void distributePlayers(String gameRoomId) {
        double degreesOffset = 0;
        int padding = 30; // Minimum amount of 'pixels' from the wall that a player can spawn at

        // Get grid size and how many players there are
        GameRoom gameRoom = getGameRoomById(gameRoomId);
        int gridSize = gameRoom.getGridSize();
        int playerCount = gameRoom.getPlayers().size();

        if (playerCount == 0)
            return; // will cause division by 0 otherwise

        // Get degrees between each player
        double degreesBetweenPlayers = 360.0 / playerCount;

        // compute radius, given gridSize and padding
        double diameter = gridSize - 2 * padding; // remove one padding on each side
        double radius = (double) diameter / 2;

        // Get center position (roughly)
        PositionDTO center = new PositionDTO(
                (int) (gridSize / 2),
                (int) (gridSize / 2));

        // Get points on a circle within the grid size (with some padding on the sides),
        // and convert them into integer positions x and y
        int currentPlayerIndex = 0;
        for (Player player : gameRoom.getPlayers().values()) {
            // generate position
            double positionAngle = currentPlayerIndex * degreesBetweenPlayers + degreesOffset;
            PositionDTO playerPosition = new PositionDTO(
                    (int) (center.x() + Math.cos(Math.toRadians(positionAngle)) * radius),
                    (int) (center.y() + Math.sin(Math.toRadians(positionAngle)) * radius));
            // set players position
            player.setCurrentX(playerPosition.x());
            player.setCurrentY(playerPosition.y());

            // increment counter
            currentPlayerIndex++;
        }
    }

}
