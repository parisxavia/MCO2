import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Player {
    
    // Position and movement
    private int worldX, worldY;
    private int speed;
    
    // Health system
    private int maxHealth;
    private int currentHealth;
    
    // Visual
    private BufferedImage playerImage;
    
    // Game constants
    private final int tileSize;
    private final int screenWidth;
    private final int screenHeight;
    
    public Player(int tileSize, int screenWidth, int screenHeight) {
        this.tileSize = tileSize;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        
        setDefaultValues();
        loadPlayerImage();
    }
    
    private void setDefaultValues() {
        worldX = tileSize * 4; // Start at grid position (4,4)
        worldY = tileSize * 4;
        speed = tileSize; // Move exactly one tile per input
        
        // Health initialization
        maxHealth = 3;
        currentHealth = maxHealth;
    }
    
    private void loadPlayerImage() {
        try {
            playerImage = ImageIO.read(getClass().getResourceAsStream("resources/sprites/Player.png"));
        } catch(IOException e) {
            System.err.println("Could not load player image: " + e.getMessage());
        }
    }
    
    // Movement methods
    public void moveUp() {
        worldY -= speed;
        keepInBounds();
    }
    
    public void moveDown() {
        worldY += speed;
        keepInBounds();
    }
    
    public void moveLeft() {
        worldX -= speed;
        keepInBounds();
    }
    
    public void moveRight() {
        worldX += speed;
        keepInBounds();
    }
    
    private void keepInBounds() {
        if(worldX < 0) {
            worldX = 0;
        }
        if(worldY < 0) {
            worldY = 0;
        }
        if(worldX >= screenWidth - tileSize) {
            worldX = screenWidth - tileSize;
        }
        if(worldY >= screenHeight - tileSize) {
            worldY = screenHeight - tileSize;
        }
    }
    
    // Health management methods
    public void takeDamage(int damage) {
        currentHealth -= damage;
        if(currentHealth < 0) {
            currentHealth = 0;
        }
    }
    
    public void heal(int amount) {
        currentHealth += amount;
        if(currentHealth > maxHealth) {
            currentHealth = maxHealth;
        }
    }
    
    public boolean isAlive() {
        return currentHealth > 0;
    }
    
    // Getters
    public int getWorldX() {
        return worldX;
    }
    
    public int getWorldY() {
        return worldY;
    }
    
    public int getCurrentHealth() {
        return currentHealth;
    }
    
    public int getMaxHealth() {
        return maxHealth;
    }
    
    public void draw(Graphics2D g2) {
        g2.drawImage(playerImage, worldX, worldY, tileSize, tileSize, null);
    }
}
