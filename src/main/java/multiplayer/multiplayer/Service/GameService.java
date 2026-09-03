package multiplayer.multiplayer.Service;

import multiplayer.multiplayer.controller.GameController;

import org.springframework.stereotype.Service;

@Service
public class GameService {

    private final GameController gameController;

    GameService(GameController gameController) {
        this.gameController = gameController;
    }

    private boolean updatePlayerDirection(String playerId, String direction) {
        // hantera logik för att svänga, ta imot spelaren valda riktning samt spelarens
        // id ifrån klienten
        // uppdatera ny riktning på spelare

        // Anledning till att detta är en boolean är för att vi skall retunera false om
        // spelaren inte kan göra den valda svängen pga t.ex att man kör south och vill
        // svänga north
        return null;
    }

    private boolean Turn(Player Player) {

        // Kolla spelarens senaste postion och med hjälp av vald riktning
        // ändra nästa position och kolla om det blir en kollition med hjälp utav
        // hasPlayerColided(null)
        // Om man får tillbaka false lägger man till den nya positionen i gameRoom.list

        // anledningen att detta är en boolean är för att vi vill veta om svängen
        // lyckades.
        return null;
    }

    public Map<String, GameRoom> tick(String GameroomId) {
        // Returnera map med alla spelare i gameroomets positioner

    }

    private boolean hasPlayerColided(Player player) {
        // kolla om spelare har krockat igenom att kolla spelarens position är och
        // jämför med befintliga positioner i gameRoomets lista
    }
}
