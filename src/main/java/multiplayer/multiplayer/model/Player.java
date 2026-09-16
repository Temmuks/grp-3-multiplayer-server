package multiplayer.multiplayer.model;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class Player {
    String playerId = UUID.randomUUID().toString();

    int currentX = 50;
    int currentY = 50;

    String direction = List.of("up", "down", "left", "right").get((int) (Math.random() * 4));

    boolean isAlive = true;

    String color;

    int currentDashTicksLeft = 0;

    int dashesLeft = 3;

    Instant createdAt = Instant.now();

    // TBD
    // int points;
    // TBD
    // String Powerup;

    public Player(String playerId, int currentX, int currentY, String direction, boolean isAlive, String color, int currentDashTicksLeft, int dashesLeft) {
        this.playerId = playerId;
        this.currentX = currentX;
        this.currentY = currentY;
        this.direction = direction;
        this.isAlive = isAlive;
        this.color = color;
        this.currentDashTicksLeft = currentDashTicksLeft;
        this.dashesLeft = dashesLeft;
        
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
    
    public int getCurrentDashTicksLeft() {
        return currentDashTicksLeft;
    }
    
    public void setCurrentDashTicksLeft(int currentDashTicksLeft) {
        this.currentDashTicksLeft = currentDashTicksLeft;
    }
    
    public int getDashesLeft() {
        return dashesLeft;
    }
    
    public void setDashesLeft(int dashesLeft) {
        this.dashesLeft = dashesLeft;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
    
}
