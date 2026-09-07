package multiplayer.multiplayer.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import multiplayer.multiplayer.dto.PositionDTO;

public class GameRoom {

    List<String> colors = new ArrayList<>(List.of("crimson",
            "royalblue",
            "limegreen",
            "darkorange",
            "blueviolet",
            "deepskyblue",
            "gold",
            "deeppink",
            "saddlebrown",
            "darkcyan",
            "olivedrab",
            "indigo",
            "tomato",
            "slategray",
            "springgreen"));

    String gameRoomId;
    Map<String, Player> players = new HashMap<>();

    // Varje position som någon gång under spelets gång har upptagits av en spelare
    // kommer att sparas här, där nyckeln är position som PositionDTO som består av
    // x och y,
    // och värdet är en String som är ett playerId. (kan exempelvis användas för att
    // visa *vem* man kolliderat med)
    Map<PositionDTO, String> previousPositions = new HashMap<>();
    int gridSize;
    String gameRoomStatus;
    int maxPlayers;
    String gameRoomOwner;

    public GameRoom(List<String> colors, String gameRoomId, Map<String, Player> players, int gridSize,
            String gameRoomStatus, int maxPlayers) {
        this.colors = colors;
        this.gameRoomId = gameRoomId;
        this.players = players;
        this.previousPositions = new HashMap<>();
        this.gridSize = gridSize;
        this.gameRoomStatus = gameRoomStatus;
        this.maxPlayers = maxPlayers;
    }

    public GameRoom() {
    }

    public String getGameRoomOwner() {
        return gameRoomOwner;
    }

    public void setGameRoomOwner(String gameRoomOwner) {
        this.gameRoomOwner = gameRoomOwner;
    }

    public String getGameRoomId() {
        return gameRoomId;
    }

    public void setGameRoomId(String gameRoomId) {
        this.gameRoomId = gameRoomId;
    }

    public Map<String, Player> getPlayers() {
        return players;
    }

    public void setPlayers(Map<String, Player> players) {
        this.players = players;
    }

    public Map<PositionDTO, String> getPreviousPositions() {
        return previousPositions;
    }

    public void setPreviousPositions(Map<PositionDTO, String> previousPositions) {
        this.previousPositions = previousPositions;
    }

    public int getGridSize() {
        return gridSize;
    }

    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }

    public String getGameRoomStatus() {
        return gameRoomStatus;
    }

    public void setGameRoomStatus(String gameRoomStatus) {
        this.gameRoomStatus = gameRoomStatus;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public List<String> getColors() {
        return colors;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }

    // TBD
    // String winner;
    // List Leaderboard;

}
