package multiplayer.multiplayer.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.springframework.stereotype.Service;

import multiplayer.multiplayer.dto.CreateGameRoomDTO;
import multiplayer.multiplayer.dto.DashDTO;
import multiplayer.multiplayer.dto.GameRoomUpdateDTO;
import multiplayer.multiplayer.dto.PlayerUpdateDTO;
import multiplayer.multiplayer.dto.PositionDTO;
import multiplayer.multiplayer.dto.SetGameRoomStatusDTO;
import multiplayer.multiplayer.dto.TurnDTO;
import multiplayer.multiplayer.enums.GameState;
import multiplayer.multiplayer.model.GameRoom;
import multiplayer.multiplayer.model.Player;

@Service
public class GameService {

    private List<GameRoom> gameRoomList = new ArrayList<>();

    GameService() {
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
            // Move player
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
        
        // increment tick count
        gameRoom.setTick(gameRoom.getTick()+1);

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

    public void activateDash(DashDTO dashDTO){
        Player player = getPlayerById(dashDTO.playerId(), dashDTO.gameRoomId());
        int ticksOfDash = 100; // adjust this later. 
        if (player.getDashesLeft() <= 0) return; // No more dashes left
        
        player.setCurrentDashTicksLeft(player.getCurrentDashTicksLeft() + ticksOfDash);
        
        // decrement dashes left
        player.setDashesLeft(player.getDashesLeft()-1);
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

    public void deleteAllGameRooms(){
        gameRoomList = new ArrayList<>();
    }

    public Player createPlayer(String gameRoomId) {
        // Hämta gameroomet
        GameRoom gameRoom = getGameRoomById(gameRoomId);

        // Kolla om maxgräns redan är uppnådd
        if (gameRoom.getPlayers().size() >= gameRoom.getMaxPlayers()){
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
        GameRoom gameRoom = getGameRoomById(gameRoomId);

        Random rand = new Random();

        int i = rand.nextInt(gameRoom.getColors().size());

        String asignColor = gameRoom.getColors().get(i);

        gameRoom.getColors().remove(i);

        player.setColor(asignColor);
    }

    public void distributePlayers(String gameRoomId){
        double degreesOffset = 0;
        int padding = 10; // Minimum amount of 'pixels' from the wall that a player can spawn at

        // Get grid size and how many players there are
        GameRoom gameRoom = getGameRoomById(gameRoomId);
        int gridSize = gameRoom.getGridSize();
        int playerCount = gameRoom.getPlayers().size();
        
        if (playerCount == 0) return; // will cause division by 0 otherwise

        // Get degrees between each player
        double degreesBetweenPlayers = 360.0/playerCount;

        // compute radius, given gridSize and padding
        double diameter = gridSize - 2*padding; // remove one padding on each side
        double radius = (double) diameter/2;

        // Get center position (roughly)
        PositionDTO center = new PositionDTO(
            (int) (gridSize / 2),
            (int) (gridSize / 2)
        );
        
        // Get points on a circle within the grid size (with some padding on the sides), 
        // and convert them into integer positions x and y
        int currentPlayerIndex = 0;
        for (Player player : gameRoom.getPlayers().values()){
            // generate position
            double positionAngle = currentPlayerIndex*degreesBetweenPlayers + degreesOffset;
            PositionDTO playerPosition = new PositionDTO(
                (int) (center.x() + Math.cos(Math.toRadians(positionAngle))* radius),
                (int) (center.y() + Math.sin(Math.toRadians(positionAngle)) * radius)
            );
            // set players position
            player.setCurrentX(playerPosition.x());
            player.setCurrentY(playerPosition.y());

            // increment counter
            currentPlayerIndex++;
        }
    }

    public GameRoom startGameRoom(SetGameRoomStatusDTO setGameRoomStatusDTO) {
        GameRoom gameRoom = getGameRoomById(setGameRoomStatusDTO.gameRoomId());
        if (gameRoom.getGameRoomOwner().equals(setGameRoomStatusDTO.clientId())) {
            distributePlayers(gameRoom.getGameRoomId());
            gameRoom.setGameRoomStatus(setGameRoomStatusDTO.gameState());
        }
        return gameRoom;
    }
}
