package multiplayer.multiplayer.model;

import java.util.HashMap;
import java.util.Map;

public class GameRoom {
    String[] colors = { "crimson",
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
            "springgreen" };

    String gameRoomId;
    Map<String, Player> players = new HashMap<>();
    int gridSize;
    String gameRoomStatus;
    int maxPlayers;
    String gameRoomOwner;

    public GameRoom(String[] colors, String gameRoomId, Map<String, Player> players, int gridSize,
            String gameRoomStatus, int maxPlayers) {
        this.colors = colors;
        this.gameRoomId = gameRoomId;
        this.players = players;
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

    public String[] getColors() {
        return colors;
    }

    public void setColors(String[] colors) {
        this.colors = colors;
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

    // TBD
    // String winner;
    // List Leaderboard;

}
