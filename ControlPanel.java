import java.awt.*;
import javax.swing.*;

public class ControlPanel extends JPanel {
    
    private final GameController gameController;
    private JButton upButton, downButton, leftButton, rightButton;
    private JLabel titleLabel, healthLabel;
    private JPanel healthPanel, buttonPanel;
    private JLabel[] heartLabels;
    
    public ControlPanel(GameController gameController) {
        this.gameController = gameController;
        
        setPreferredSize(new Dimension(220, 720));
        setBackground(new Color(45, 45, 45));
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
        
        setupComponents();
    }
    
    private void setupComponents() {
        // Top section with title and health
        JPanel topPanel = createTopSection();
        add(topPanel, BorderLayout.NORTH);
        
        // Center section with movement controls
        JPanel centerPanel = createControlSection();
        add(centerPanel, BorderLayout.CENTER);
        
        // Update health display initially
        updateHealthDisplay();
        
        // Start timer for health updates
        Timer healthTimer = new Timer(100, e -> updateHealthDisplay());
        healthTimer.start();
    }
    
    private JPanel createTopSection() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(new Color(45, 45, 45));
        
        // Title
        titleLabel = new JLabel("GAME CONTROLS");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        topPanel.add(titleLabel);
        
        topPanel.add(Box.createVerticalStrut(20));
        
        // Health section
        healthLabel = new JLabel("HEALTH");
        healthLabel.setForeground(new Color(255, 200, 200));
        healthLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        healthLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        topPanel.add(healthLabel);
        
        topPanel.add(Box.createVerticalStrut(10));
        
        // Health hearts
        healthPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        healthPanel.setBackground(new Color(45, 45, 45));
        heartLabels = new JLabel[3];
        
        for(int i = 0; i < 3; i++) {
            heartLabels[i] = new JLabel("♥");
            heartLabels[i].setForeground(Color.RED);
            heartLabels[i].setFont(new Font("Arial", Font.BOLD, 24));
            healthPanel.add(heartLabels[i]);
        }
        
        topPanel.add(healthPanel);
        topPanel.add(Box.createVerticalStrut(30));
        
        return topPanel;
    }
    
    private JPanel createControlSection() {
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBackground(new Color(45, 45, 45));
        
        // Instructions
        JLabel instructionLabel = new JLabel("<html><center>Click buttons to move</center></html>");
        instructionLabel.setForeground(new Color(180, 180, 180));
        instructionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        controlPanel.add(instructionLabel);
        
        controlPanel.add(Box.createVerticalStrut(20));
        
        // Movement button grid
        buttonPanel = createMovementButtons();
        controlPanel.add(buttonPanel);
        
        return controlPanel;
    }
    

    
    private JPanel createMovementButtons() {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridBagLayout());
        buttonsPanel.setBackground(new Color(45, 45, 45));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Up button
        upButton = createStyledButton("▲", "Move Up");
        gbc.gridx = 1; gbc.gridy = 0;
        buttonsPanel.add(upButton, gbc);
        
        // Left button  
        leftButton = createStyledButton("◄", "Move Left");
        gbc.gridx = 0; gbc.gridy = 1;
        buttonsPanel.add(leftButton, gbc);
        
        // Down button
        downButton = createStyledButton("▼", "Move Down");
        gbc.gridx = 1; gbc.gridy = 1;
        buttonsPanel.add(downButton, gbc);
        
        // Right button
        rightButton = createStyledButton("►", "Move Right");
        gbc.gridx = 2; gbc.gridy = 1;
        buttonsPanel.add(rightButton, gbc);
        
        return buttonsPanel;
    }
    
    private JButton createStyledButton(String symbol, String tooltip) {
        JButton button = new JButton(symbol);
        button.setPreferredSize(new Dimension(60, 60));
        button.setFont(new Font("Arial", Font.BOLD, 24));
        button.setFocusable(false);
        button.setToolTipText(tooltip);
        
        // Modern button styling
        button.setBackground(new Color(70, 70, 70));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(100, 100, 100), 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
        // Hover effects
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(90, 90, 90));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(70, 70, 70));
            }
        });
        
        // Add action listener for movement
        button.addActionListener(e -> handleButtonPress(symbol));
        
        return button;
    }
    
    private void handleButtonPress(String symbol) {
        Player player = gameController.getGameState().getPlayer();
        
        // Direct player movement without using InputHandler
        switch(symbol) {
            case "▲" -> player.moveUp();
            case "▼" -> player.moveDown();
            case "◄" -> player.moveLeft();
            case "►" -> player.moveRight();
        }
        
        // Visual feedback - briefly highlight the button
        JButton pressedButton = getButtonBySymbol(symbol);
        if (pressedButton != null) {
            Color originalColor = pressedButton.getBackground();
            pressedButton.setBackground(new Color(120, 180, 120));
            Timer resetTimer = new Timer(150, e -> pressedButton.setBackground(originalColor));
            resetTimer.setRepeats(false);
            resetTimer.start();
        }
    }
    
    private JButton getButtonBySymbol(String symbol) {
        switch(symbol) {
            case "▲" -> { 
                return upButton; }
            case "▼" -> { 
                return downButton; }
            case "◄" -> { 
                return leftButton; }
            case "►" -> { 
                return rightButton; }
            default -> { 
                return null; }
        }
    }
    
    private void updateHealthDisplay() {
        Player player = gameController.getGameState().getPlayer();
        int currentHealth = player.getCurrentHealth();
        for(int i = 0; i < heartLabels.length; i++) {
            if(i < currentHealth) {
                heartLabels[i].setForeground(Color.RED); // Full heart
            } else {
                heartLabels[i].setForeground(Color.DARK_GRAY); // Empty heart
            }
        }
    }
}
