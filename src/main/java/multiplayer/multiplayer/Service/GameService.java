package multiplayer.multiplayer.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import multiplayer.multiplayer.dto.CreateGameRoomDTO;
import multiplayer.multiplayer.dto.GameRoomUpdateDTO;
import multiplayer.multiplayer.dto.PlayerUpdateDTO;
import multiplayer.multiplayer.dto.PositionDTO;
import multiplayer.multiplayer.enums.GameState;
import multiplayer.multiplayer.model.GameRoom;
import multiplayer.multiplayer.model.Player;

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

        // Lever spelaren?
        if (!player.isAlive()) {
            return false;
        }

        // Har spelaren kolliderat?
        // if(hasPlayerColided(player, gameRoom)){
        // player.setAlive(false);
        // // När en spelare dör kan winner-läget ändras.
        // //ska fungera när vi löser kollisionslogiken.
        // }

        // Flytta spelaren
        switch (player.getDirection()) {
            case "up":
                player.setCurrentY(player.getCurrentY() - 1);
                break;
            case "down":
                player.setCurrentY(player.getCurrentY() + 1);
                break;
            case "left":
                player.setCurrentX(player.getCurrentX() - 1);
                break;
            case "right":
                player.setCurrentX(player.getCurrentX() + 1);
                break;

            default:
                break;
        }

        // Kolla spelarens senaste postion och med hjälp av vald riktning
        // ändra nästa position och kolla om det blir en kollition med hjälp utav
        // hasPlayerColided(null)
        // Om man får tillbaka false lägger man till den nya positionen i gameRoom.list

        // anledningen att detta är en boolean är för att vi vill veta om svängen
        // lyckades.
        return true;
    }

    public GameRoomUpdateDTO tick(String gameRoomId) {
        // kolla att rummet är IN_PROGRESS
        GameRoomUpdateDTO gameRoomUpdateDTO = new GameRoomUpdateDTO();

        // Hämtar rätt rum via gameRoomId och kör en tick för just det rummet.
        GameRoom gameRoom = getGameRoomById(gameRoomId);

        gameRoomUpdateDTO.setGameRoomStatus(gameRoom.getGameRoomStatus());
        // Applicera alla spelares nya positioner (inkl kolla kollisioner)
        for (Player player : gameRoom.getPlayers().values()) {
            applyMovement(player);

            PositionDTO positionDTO = new PositionDTO(player.getCurrentX(), player.getCurrentY());

            PlayerUpdateDTO playerUpdateDTO = new PlayerUpdateDTO(player.getPlayerId(), player.getColor(), positionDTO);

            gameRoomUpdateDTO.getPlayerUpdateDTOList().add(playerUpdateDTO);

            // gameRoomUpdateDTO.getPlayerPositions().put(
            // new PositionDTO(player.getCurrentX(), player.getCurrentY()),
            // player.getPlayerId());
            // gameRoomUpdateDTO.getPlayerColors().put(player.getPlayerId(),
            // player.getColor());
        }

        // Kolla om någon vinnare finns
        if (checkWinner(gameRoom) != null) {
            gameRoomUpdateDTO.setGameRoomStatus(GameState.FINISHED);
            // gameRoomUpdateDTO.setWinner(blabla)
        }

        return gameRoomUpdateDTO;
        // Returnera map med alla spelare i gameroomets positioner
    }

    private boolean hasPlayerColided(Player player, GameRoom gameRoom) {
        // kolla om spelare har krockat igenom att kolla spelarens position är och
        // jämför med befintliga positioner i gameRoomets lista
        return false; // temporär return för att kunna sätta igång servern
    }

    public Player checkWinner(GameRoom gameRoom) {
        // Avsluta direkyt om rummet redan är färdigspelat
        // if(GameState.FINISHED.equals(gameRoom.getGameRoomStatus())){
        // return null;
        // }

        if (!gameRoom.getGameRoomStatus().equals(GameState.IN_PROGRESS)) {
            return null;
        }
        List<Player> alivePlayers = gameRoom.getPlayers().values().stream()
                .filter(Player::isAlive).toList();

        if (alivePlayers.size() == 1) {
            // om bara en spelare är kvar alive sätter vi winner som playerId på den
            // spelöaren
            // och ändrar status på rummet till "FINISHED".
            Player winner = alivePlayers.get(0);
            gameRoom.setWinner(winner.getPlayerId());
            gameRoom.setGameRoomStatus(GameState.FINISHED);
            return winner;
        }
        return null;
    }

    // Detta är lite till för att frontend bara behöver veta färgen på vinnaren typ
    // Alltså ger färgen på vinnaren om den finns, finns den ej returneras null.
    public String getWinnerColor(GameRoom gameRoom) {
        String winnerId = gameRoom.getWinner();
        if (winnerId != null) {
            Player winnerPlayer = gameRoom.getPlayers().get(winnerId);
            if (winnerPlayer != null) {
                return winnerPlayer.getColor();
            }
        }
        return null;
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

    public GameRoom createGameRoom(CreateGameRoomDTO createGameRoomDTO) {
        GameRoom gameRoom = new GameRoom();
        gameRoom.setGameRoomStatus(GameState.NOT_STARTED);
        gameRoom.setMaxPlayers(createGameRoomDTO.getMaxPlayers());
        gameRoom.setGameRoomOwner(createGameRoomDTO.getClientId());
        gameRoom.setGridSize(createGameRoomDTO.getMaxPlayers() * 64);// Sätter gridsize baserat på max antal spelare. 4
                                                                     // = 256, 10 = 640, 15 =
        // 960 etc.
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
