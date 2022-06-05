package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Data {
    // Attribute
    private GamePanel gamePanel;

    // Constructor
    public Data(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    // Saving the game's data to file
    protected void saveData() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("data.txt"));

            // Total Coin
            bw.write(String.valueOf(gamePanel.getCharacterUpgrade().getTotalCoin()));
            bw.newLine();

            // Strength
            bw.write(String.valueOf(gamePanel.getPlayer().getStrength()));
            bw.newLine();
            // Speed
            bw.write(String.valueOf(gamePanel.getPlayer().getSpeed()));
            bw.newLine();
            // Max Life
            bw.write(String.valueOf(gamePanel.getPlayer().getMaxLife()));
            bw.newLine();

            // Strength Cost
            bw.write(String.valueOf(gamePanel.getCharacterUpgrade().getStrengthCost()));
            bw.newLine();
            // Speed Cost
            bw.write(String.valueOf(gamePanel.getCharacterUpgrade().getSpeedCost()));
            bw.newLine();
            // HP Cost
            bw.write(String.valueOf(gamePanel.getCharacterUpgrade().getHpCost()));
            bw.newLine();

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Loading the game's data from file
    protected void loadData() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("data.txt"));

            // Total Coin
            String s = br.readLine();
            gamePanel.getCharacterUpgrade().setTotalCoin(Integer.parseInt(s));

            // Strength
            s = br.readLine();
            gamePanel.getPlayer().setStrength(Integer.parseInt(s));
            // Speed
            s = br.readLine();
            gamePanel.getPlayer().setSpeed(Integer.parseInt(s));
            // Max Life
            s = br.readLine();
            gamePanel.getPlayer().setMaxLife(Integer.parseInt(s));

            // Strength Cost
            s = br.readLine();
            gamePanel.getCharacterUpgrade().setStrengthCost(Integer.parseInt(s));
            // Speed Cost
            s = br.readLine();
            gamePanel.getCharacterUpgrade().setSpeedCost(Integer.parseInt(s));
            // HP Cost
            s = br.readLine();
            gamePanel.getCharacterUpgrade().setHpCost(Integer.parseInt(s));

            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}