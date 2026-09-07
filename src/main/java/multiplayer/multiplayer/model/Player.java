package multiplayer.multiplayer.model;

import java.util.UUID;

public class Player {
    String playerId = UUID.randomUUID().toString();

    int currentX;

    int currentY;

    String direction;

    boolean isAlive = true;

    String color;

    // TBD
    // int points;
    // TBD
    // String Powerup;

    public Player(String playerId, int currentX, int currentY, String direction, boolean isAlive, String color) {
        this.playerId = playerId;
        this.currentX = currentX;
        this.currentY = currentY;
        this.direction = direction;
        this.isAlive = isAlive;
        this.color = color;
    }

    public Player() {
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public int getCurrentX() {
        return currentX;
    }

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    public int getCurrentY() {
        return currentY;
    }

    public void setCurrentY(int currentY) {
        this.currentY = currentY;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
