public class GameController {
    
    private final GameState gameState;
    
    public GameController() {
        gameState = new GameState();
    }
    
    public void update() {
        // Input is now handled directly by ControlPanel buttons
        gameState.update();
    }
    
    // Getters
    public GameState getGameState() {
        return gameState;
    }
}
