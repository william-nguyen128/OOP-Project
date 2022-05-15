package main;

import entity.Player;
import main.GamePanel;

import java.io.*;

public class Coin {
    GamePanel gamePanel;
    public Coin(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }



    public void saveCoin(){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Users\\thanh\\Downloads\\project\\OOP-Project\\coin.txt"));

            bw.write(String.valueOf(gamePanel.getPlayer().getCoin()));
            bw.newLine();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void loadCoin(){
        try {
            BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\thanh\\Downloads\\project\\OOP-Project\\coin.txt"));

            String s = br.readLine();
            gamePanel.getPlayer().setCoin(Integer.parseInt(s));
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}