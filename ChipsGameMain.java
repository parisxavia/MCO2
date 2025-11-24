import java.awt.BorderLayout;
import javax.swing.JFrame;

public class ChipsGameMain {

    public static void main(String[] args) {
        
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("MCO2 - Chip's Game (MVC Architecture)");
        window.setLayout(new BorderLayout());
        
        // Create the game panel (contains the controller)
        GamePanel gamePanel = new GamePanel();
        
        // Create the control panel with reference to the game controller
        ControlPanel controlPanel = new ControlPanel(gamePanel.getGameController());
        
        window.add(gamePanel, BorderLayout.CENTER);
        window.add(controlPanel, BorderLayout.EAST);
        
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        
        // Start the game
        gamePanel.startGameThread();
    }
}
