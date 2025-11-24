public class GameState {
    
    // Game constants
    public static final int ORIGINAL_TILE_SIZE = 16;
    public static final int SCALE = 5;
    public static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;
    public static final int MAX_COL = 9;
    public static final int MAX_ROW = 9;
    public static final int SCREEN_WIDTH = TILE_SIZE * MAX_COL;
    public static final int SCREEN_HEIGHT = TILE_SIZE * MAX_ROW;
    public static final int FPS = 60;
    
    // Game entities
    private Player player;
    
    // Game status
    private boolean gameRunning;
    
    public GameState() {
        initialize();
    }
    
    private void initialize() {
        player = new Player(TILE_SIZE, SCREEN_WIDTH, SCREEN_HEIGHT);
        gameRunning = true;
    }
    
    public void update() {
        // Update game logic here
        if (!player.isAlive()) {
            gameRunning = false;
        }
    }
    
    // Getters and setters
    public Player getPlayer() {
        return player;
    }
    
    public boolean isGameRunning() {
        return gameRunning;
    }
    
    public void setGameRunning(boolean gameRunning) {
        this.gameRunning = gameRunning;
    }

}
