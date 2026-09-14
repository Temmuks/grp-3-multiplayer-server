package multiplayer.multiplayer.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Service;

import multiplayer.multiplayer.dto.DashDTO;
import multiplayer.multiplayer.dto.GameRoomUpdateDTO;
import multiplayer.multiplayer.dto.PlayerUpdateDTO;
import multiplayer.multiplayer.dto.PositionDTO;
import multiplayer.multiplayer.dto.TurnDTO;
import multiplayer.multiplayer.enums.GameState;
import multiplayer.multiplayer.model.GameRoom;
import multiplayer.multiplayer.model.Player;

@Service
public class GameService {

    GameRoomService gameRoomService = new GameRoomService();

    GameService(GameRoomService gameRoomService) {
        this.gameRoomService = gameRoomService;
    }

    public boolean updatePlayerDirection(TurnDTO turnDTO) {

        Player player = getPlayerById(turnDTO.getPlayerId(), turnDTO.getGameRoomId());

        if (!checkTurnUnavaiable(turnDTO.getDirection(), player.getDirection())) {
            player.setDirection(turnDTO.getDirection());
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
        int movementAmount = 1;

        // Lever spelaren?
        if (!player.isAlive()) {
            return false;
        }
        // Move 2 units if the player has dashTicks left
        if (player.getCurrentDashTicksLeft() > 0) {
            player.setCurrentDashTicksLeft(player.getCurrentDashTicksLeft() - 1);
            movementAmount = 2;
        }

        // Flytta spelaren
        switch (player.getDirection()) {
            case "up":
                player.setCurrentY(player.getCurrentY() - movementAmount);
                break;
            case "down":
                player.setCurrentY(player.getCurrentY() + movementAmount);
                break;
            case "left":
                player.setCurrentX(player.getCurrentX() - movementAmount);
                break;
            case "right":
                player.setCurrentX(player.getCurrentX() + movementAmount);
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

    // Turn a PositionChangeDTO into a list of PositionDTO of all possible positions
    // between start and end, inclusive.
    private List<PositionDTO> toPositionDTOList(PositionDTO pos1, PositionDTO pos2) {
        List<PositionDTO> positionDTOs = new ArrayList<>();
        int minX = Math.min(pos1.x(), pos2.x());
        int maxX = Math.max(pos1.x(), pos2.x());

        int minY = Math.min(pos1.y(), pos2.y());
        int maxY = Math.max(pos1.y(), pos2.y());
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                positionDTOs.add(new PositionDTO(x, y));
            }
        }
        return positionDTOs;
    }

    public GameRoomUpdateDTO tick(String gameRoomId) {
        // kolla att rummet är IN_PROGRESS
        GameRoomUpdateDTO gameRoomUpdateDTO = new GameRoomUpdateDTO();

        // Hämtar rätt rum via gameRoomId och kör en tick för just det rummet.
        GameRoom gameRoom = gameRoomService.getGameRoomById(gameRoomId);

        gameRoomUpdateDTO.setGameRoomStatus(gameRoom.getGameRoomStatus());

        // Make a list of occupiedPositions. We will fill this map for each player
        // first, then add it to the gameRooms's previousPositions map.
        Map<PositionDTO, String> newOccupiedPositions = new HashMap<>();
        // Applicera alla spelares nya positioner (inkl kolla kollisioner)
        for (Player player : gameRoom.getPlayers().values()) {
            // Move player
            PositionDTO positionBefore = new PositionDTO(player.getCurrentX(), player.getCurrentY());
            applyMovement(player);
            PositionDTO positionAfter = new PositionDTO(player.getCurrentX(), player.getCurrentY());

            List<PositionDTO> playerPositions = toPositionDTOList(positionBefore, positionAfter);
            playerPositions.forEach(pcDTO -> {
                newOccupiedPositions.put(pcDTO, player.getPlayerId());
            });
            PlayerUpdateDTO playerUpdateDTO = new PlayerUpdateDTO(player.getPlayerId(), player.getColor(),
                    playerPositions);

            gameRoomUpdateDTO.getPlayerUpdateDTOList().add(playerUpdateDTO);

            // gameRoomUpdateDTO.getPlayerPositions().put(
            // new PositionDTO(player.getCurrentX(), player.getCurrentY()),
            // player.getPlayerId());
            // gameRoomUpdateDTO.getPlayerColors().put(player.getPlayerId(),
            // player.getColor());
        }

        // Check collisions
        for (Player player : gameRoom.getPlayers().values()) {
            if (!player.isAlive())
                continue;

            player.setAlive(!hasPlayerCollided(player, gameRoom)); // if has collided, set player as dead
        }

        // Add their previous positions to the gamerooms list
        gameRoom.getPreviousPositions().putAll(newOccupiedPositions);

        // Kolla om någon vinnare finns
        if (checkWinner(gameRoom) != null) {
            gameRoomUpdateDTO.setGameRoomStatus(GameState.FINISHED);
            // gameRoomUpdateDTO.setWinner(blabla)
        }

        // increment tick count
        gameRoom.setTick(gameRoom.getTick() + 1);

        return gameRoomUpdateDTO;
        // Returnera map med alla spelare i gameroomets positioner
    }

    private boolean hasPlayerCollided(Player player, GameRoom gameRoom) {
        // kolla om spelare har krockat igenom att kolla spelarens position är och
        // jämför med befintliga positioner i gameRoomets lista

        // Skapa DTO att jämföra med
        PositionDTO playerPosition = new PositionDTO(player.getCurrentX(), player.getCurrentY());

        boolean hasCollidedWithOtherPlayer = gameRoom.getPreviousPositions().containsKey(playerPosition);
        boolean hasCollidedWithWall = (playerPosition.x() < 0 // left wall
                || playerPosition.x() >= gameRoom.getGridSize() // right wall
                || playerPosition.y() < 0 // upper wall
                || playerPosition.y() >= gameRoom.getGridSize()); // lower wall

        return hasCollidedWithOtherPlayer || hasCollidedWithWall;
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

    public void activateDash(DashDTO dashDTO) {
        Player player = getPlayerById(dashDTO.playerId(), dashDTO.gameRoomId());
        int ticksOfDash = 50; // adjust this later.
        if (player.getDashesLeft() <= 0)
            return; // No more dashes left

        player.setCurrentDashTicksLeft(player.getCurrentDashTicksLeft() + ticksOfDash);

        // decrement dashes left
        player.setDashesLeft(player.getDashesLeft() - 1);
    }

    public Player getPlayerById(String playerId, String gameRoomId) {
        GameRoom gameRoom = gameRoomService.getGameRoomById(gameRoomId);

        Map<String, Player> players = gameRoom.getPlayers();

        Player player = players.get(playerId);

        return player;
    }

    public Player createPlayer(String gameRoomId) {
        // Hämta gameroomet
        GameRoom gameRoom = gameRoomService.getGameRoomById(gameRoomId);

        // Kolla om maxgräns redan är uppnådd
        if (gameRoom.getPlayers().size() >= gameRoom.getMaxPlayers()) {
            return null;
        }

        // skapa spelare
        Player player = new Player();
        asignColorToPlayer(player, gameRoomId);

        // lägg till spelaren i gameroomet
        gameRoom.getPlayers().put(player.getPlayerId(), player);
        return player;
    }

    // Hanterar färsättning utav spelare, när en spelare har fått sin färg plockas
    // den bort ifrån listan av färger i gameroomet
    // Referens till random position i listan:
    // https://www.baeldung.com/java-random-list-element
    public void asignColorToPlayer(Player player, String gameRoomId) {
        GameRoom gameRoom = gameRoomService.getGameRoomById(gameRoomId);

        Random rand = new Random();

        int i = rand.nextInt(gameRoom.getColors().size());

        String asignColor = gameRoom.getColors().get(i);

        gameRoom.getColors().remove(i);

        player.setColor(asignColor);
    }

}
