package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Config {
    // Attribute
    private GamePanel gamePanel;

    // Constructor
    public Config(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    // Saving the game's configurations to file
    protected void saveConfig() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));

            // Full Screen configuration
            if (gamePanel.isFullScreenOn() == true)
                bw.write("On");
            if (gamePanel.isFullScreenOn() == false)
                bw.write("Off");
            bw.newLine();

            // Music configuration
            bw.write(String.valueOf(gamePanel.getMusic().getVolumeScale()));
            bw.newLine();

            // Sound Effects configuration
            bw.write(String.valueOf(gamePanel.getSoundEffect().getVolumeScale()));
            bw.newLine();

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Loading the game's configurations from file
    protected void loadConfig() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("config.txt"));

            String s = br.readLine();

            // Full Screen configuration
            if (s.equals("On"))
                gamePanel.setFullScreenOn(true);
            if (s.equals("Off"))
                gamePanel.setFullScreenOn(false);

            // Music configuration
            s = br.readLine();
            gamePanel.getMusic().setVolumeScale(Integer.parseInt(s));

            // Sound Effect configuration
            s = br.readLine();
            gamePanel.getSoundEffect().setVolumeScale(Integer.parseInt(s));

            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}