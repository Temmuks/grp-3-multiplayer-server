package multiplayer.multiplayer.Service;

import multiplayer.multiplayer.model.GameRoom;
import multiplayer.multiplayer.model.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class GameService {

    private List<GameRoom> gameRoomList = new ArrayList<>();

    GameService() {
    }

    public boolean updatePlayerDirection(String playerId, String direction, String gameRoomId) {

        Player player = getPlayerById(playerId, gameRoomId);

        if (!checkTurnUnavaiable(direction, player.getDirection())) {
            player.setDirection(direction);
            return true;
        } else {
            return false;
        }

        // hantera logik för att svänga, ta imot spelaren valda riktning samt spelarens
        // id ifrån klienten
        // uppdatera ny riktning på spelare

        // Anledning till att detta är en boolean är för att vi skall retunera false om
        // spelaren inte kan göra den valda svängen pga t.ex att man kör south och vill
        // svänga north
    }

    // Kollar om spelaren kan svänga åt valt håll, förhindrar att man kan svänga
    // motsatt riktning imot vad man redan kör
    private boolean checkTurnUnavaiable(String turnDirection, String currentDirection) {
        return currentDirection.equals("right") && turnDirection.equals("left")
                || currentDirection.equals("left") && turnDirection.equals("right")
                || currentDirection.equals("up") && turnDirection.equals("down")
                || currentDirection.equals("down") && turnDirection.equals("up");
    }

    public boolean applyMovement(Player player) {

        // Kolla spelarens senaste postion och med hjälp av vald riktning
        // ändra nästa position och kolla om det blir en kollition med hjälp utav
        // hasPlayerColided(null)
        // Om man får tillbaka false lägger man till den nya positionen i gameRoom.list

        // anledningen att detta är en boolean är för att vi vill veta om svängen
        // lyckades.
        return true;
    }

    public Map<String, GameRoom> tick(String gameRoomId) {

        // Returnera map med alla spelare i gameroomets positioner
        return new HashMap<String, GameRoom>(); // temporär return för att kunna sätta igång servern

    }

    private boolean hasPlayerColided(Player player, GameRoom gameRoom) {
        // kolla om spelare har krockat igenom att kolla spelarens position är och
        // jämför med befintliga positioner i gameRoomets lista
        return false; // temporär return för att kunna sätta igång servern
    }

    public GameRoom getGameRoomById(String gameRoomId) {
        GameRoom gameRoomById = gameRoomList.stream().filter(gr -> gr.getGameRoomId().equals(gameRoomId)).findFirst()
                .orElseThrow();
        return gameRoomById;
    }

    public Player getPlayerById(String playerId, String gameRoomId) {
        GameRoom gameRoom = getGameRoomById(gameRoomId);

        Map<String, Player> players = gameRoom.getPlayers();

        Player player = players.get(playerId);

        return player;
    }

    public List<GameRoom> getAllGameRooms() {
        return gameRoomList;
    }

    public GameRoom createGameRoom() {
        GameRoom gameRoom = new GameRoom();

        String gameRoomId = UUID.randomUUID().toString();
        gameRoom.setGameRoomId(gameRoomId);

        gameRoomList.add(gameRoom);
        return gameRoom;
    }

    public void addNewPlayer(Player player, String gameRoomId) {
        GameRoom gameRoom = getGameRoomById(gameRoomId);
        gameRoom.getPlayers().put(player.getPlayerId(), player);
    }

    // Hanterar färsättning utav spelare, när en spelare har fått sin färg plockas
    // den bort ifrån listan av färger i gameroomet
    // Referens till random position i listan:
    // https://www.baeldung.com/java-random-list-element
    public void asignColorToPlayer(Player player, String gameRoomId) {
        GameRoom gameRoom = getGameRoomById(gameRoomId);

        Random rand = new Random();

        int i = rand.nextInt(gameRoom.getColors().size());

        String asignColor = gameRoom.getColors().get(i);

        gameRoom.getColors().remove(i);

        player.setColor(asignColor);

    }
}
