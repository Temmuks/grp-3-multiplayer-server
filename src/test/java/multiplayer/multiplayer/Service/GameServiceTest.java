package multiplayer.multiplayer.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import multiplayer.multiplayer.dto.CreateGameRoomDTO;
import multiplayer.multiplayer.dto.SetGameRoomStatusDTO;
import multiplayer.multiplayer.dto.TurnDTO;
import multiplayer.multiplayer.enums.GameState;
import multiplayer.multiplayer.model.GameRoom;
import multiplayer.multiplayer.model.Player;

class GameServiceTest {

    @Test
    void shouldMovePlayerRightWhenAlive() {
        // Arrange
        GameRoomService gameRoomService = new GameRoomService();
        GameService gameService = new GameService(gameRoomService);
        GameRoom room = gameRoomService.createGameRoom(new CreateGameRoomDTO("owner-1", 4));
        Player player = gameService.createPlayer(room.getGameRoomId());
        player.setCurrentX(10);
        player.setCurrentY(20);
        player.setDirection("right");
        player.setAlive(true);

        // Act
        boolean moved = gameService.applyMovement(player);

        // Assert
        assertTrue(moved);
        assertEquals(11, player.getCurrentX());
        assertEquals(20, player.getCurrentY());
    }

    @Test
    void shouldNotMovePlayerRightWhenDead() {
        // skapa testobjekt och sätt upp startläge

        GameRoomService gameRoomService = new GameRoomService();
        GameService gameService = new GameService(gameRoomService);

        GameRoom room = gameRoomService.createGameRoom(new CreateGameRoomDTO("owner-1", 4));
        Player player = gameService.createPlayer(room.getGameRoomId());
        player.setCurrentX(10);
        player.setCurrentY(20);
        player.setDirection("right");
        player.setAlive(false);

        // kör metoden vi testar

        boolean moved = gameService.applyMovement(player);

        // förväntat resultat

        // false-koll för att spelaren inte ska röra sig
        assertFalse(moved);
        // positiionen ska vara samma annars har den rört sig
        // vilket blir fel eftersom att spelaren är dööööd!
        assertEquals(10, player.getCurrentX());
        assertEquals(20, player.getCurrentY());
    }

    // TESTAR checkTurnUnavaiable i GameService genom UpdatePlayerDirection
    @Test // eftersom att checkTurnUnavaiable är privat och inte kan testas direkt
    void shouldNotBeAbleToTurnOppositeDirection() {
        GameRoomService gameRoomService = new GameRoomService();

        GameService gameService = new GameService(gameRoomService);
        GameRoom room = gameRoomService.createGameRoom(new CreateGameRoomDTO("owner-1", 4));
        // Skapande av player + Riktning + Alive (Utgångsvärden)
        Player player = gameService.createPlayer(room.getGameRoomId());
        player.setDirection("right");
        player.setAlive(true);

        String playerId = player.getPlayerId();

        // Skapande av TurnDTO med spelarens ID och riktning och rum
        // Vilket är förändringen vi försöker göra
        TurnDTO turnDTO = new TurnDTO();
        turnDTO.setPlayerId(playerId);
        turnDTO.setDirection("left"); // Motsatt rikting till player.setDirection
        turnDTO.setGameRoomId(room.getGameRoomId());

        // kallar på det vi testar att göra med TurnDTO och ändrar boolean-värdet
        // "changedDirection"
        boolean changedDirection = gameService.updatePlayerDirection(turnDTO);

        // kollar om change lyckades
        assertFalse(changedDirection);
        // svaret vi vill ha ska vara oförändrat (right)
        assertEquals("right", player.getDirection());

    }

    @Test // TESTAR getPlayerById i GameService
    void shouldReturnCorrectPlayerById() {
        GameRoomService gameRoomService = new GameRoomService();

        GameService gameService = new GameService(gameRoomService);
        GameRoom room = gameRoomService.createGameRoom(new CreateGameRoomDTO("PlayerId", 4));

        Player player = gameService.createPlayer(room.getGameRoomId());
        
        String PlayerId = player.getPlayerId();
        
        Player result = gameService.getPlayerById(PlayerId, room.getGameRoomId());
        
        assertEquals(PlayerId, result.getPlayerId());
    }
    // Testar att en spelare med aktiv dash rör sig 2 steg istället för 1 när applyMovement körs.
    // Om currentDashTicksLeft > 0 sätts movementAmount = 2
    // Testar även att currentDashTicksLeft minskas med 1 efter movement.
    @Test
    void shouldMovePlayerTwoStepsWhenDashingPerTick() {
        // Arrange
        GameRoomService gameRoomService = new GameRoomService();
        GameService gameService = new GameService(gameRoomService);

        GameRoom room = gameRoomService.createGameRoom(
                new CreateGameRoomDTO("owner-1", 4));

        Player player = gameService.createPlayer(room.getGameRoomId());
        player.setCurrentX(10);
        player.setCurrentY(20);
        player.setDirection("right");
        player.setAlive(true);
        player.setCurrentDashTicksLeft(50);

        // Act
        boolean moved = gameService.applyMovement(player);

        // Assert
        assertTrue(moved);
        assertEquals(12, player.getCurrentX());
        assertEquals(20, player.getCurrentY());
        assertEquals(49, player.getCurrentDashTicksLeft());
    }
    
    @Test
    void shouldKillCollidingPlayerOnCollision(){
        GameRoomService gameRoomService = new GameRoomService();
    
        GameService gameService = new GameService(gameRoomService);
        String clientId = "clientid";
        GameRoom room = gameRoomService.createGameRoom(new CreateGameRoomDTO(clientId, 4));
    
        // create players
        Player player1 = gameService.createPlayer(room.getGameRoomId());
        Player collidingPlayer = gameService.createPlayer(room.getGameRoomId());
        
        // Set positions and directions
        player1.setDirection("down");
        player1.setCurrentX(3);
        player1.setCurrentY(1);
        
        collidingPlayer.setDirection("right");
        collidingPlayer.setCurrentX(0);
        collidingPlayer.setCurrentY(1);

        // Both players should be alive
        gameService.tick(room.getGameRoomId());
        assertTrue(collidingPlayer.isAlive());
        assertTrue(player1.isAlive());
        
        // Both players should be alive
        gameService.tick(room.getGameRoomId());
        assertTrue(collidingPlayer.isAlive());
        assertTrue(player1.isAlive());
        
        // The colliding player should not be alive after this tick
        gameService.tick(room.getGameRoomId());
        assertFalse(collidingPlayer.isAlive());
        assertTrue(player1.isAlive());
    }
}
