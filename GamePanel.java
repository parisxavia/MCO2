import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable {
    
    private GameController gameController;
    private Thread gameThread;
    
    public GamePanel() {
        gameController = new GameController();
        
        this.setPreferredSize(new Dimension(GameState.SCREEN_WIDTH, GameState.SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        // Removed keyboard controls - using mouse-only controls from ControlPanel
    }
    
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void run() {
        
        double drawInterval = 1000000000.0 / GameState.FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;
        
        while(gameThread != null && gameController.getGameState().isGameRunning()) {
            
            // UPDATE: update information such as character positions
            gameController.update();
            
            // DRAW: draw the screen with the updated information
            repaint();
            
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;
                
                if(remainingTime < 0) {
                    remainingTime = 0;
                }
                
                Thread.sleep((long) remainingTime);
                
                nextDrawTime += drawInterval;
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
    
    protected void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D)g;
        
        drawGrid(g2);
        drawPlayer(g2);
        
        g2.dispose();
    }
    
    private void drawGrid(Graphics2D g2) {
        // Draw grid to visualize the tile system
        g2.setColor(Color.GRAY);
        
        // Draw vertical lines
        for(int i = 0; i <= GameState.MAX_COL; i++) {
            g2.drawLine(i * GameState.TILE_SIZE, 0, i * GameState.TILE_SIZE, GameState.SCREEN_HEIGHT);
        }
        
        // Draw horizontal lines
        for(int i = 0; i <= GameState.MAX_ROW; i++) {
            g2.drawLine(0, i * GameState.TILE_SIZE, GameState.SCREEN_WIDTH, i * GameState.TILE_SIZE);
        }
    }
    
    private void drawPlayer(Graphics2D g2) {
        Player player = gameController.getGameState().getPlayer();
        player.draw(g2);
    }
    
    // Getter for the controller (used by ControlPanel)
    public GameController getGameController() {
        return gameController;
    }
}
