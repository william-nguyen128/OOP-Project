package main;

import javax.swing.JFrame;

public class Main {
    public static JFrame window;

    public static void main(String[] args) {
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Princess Rescue");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        gamePanel.getData().loadData();
        gamePanel.getCoin().loadCoin();

        gamePanel.getConfig().loadConfig();
        if (gamePanel.isFullScreenOn() == true)
            window.setUndecorated(true);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
}