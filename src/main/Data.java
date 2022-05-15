package main;

import entity.Player;

import java.io.*;

public class Data {

    GamePanel gamePanel;

    public Data(GamePanel gamePanel){
        this.gamePanel=gamePanel;
    }

    public void saveData(){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Users\\thanh\\Downloads\\project\\OOP-Project\\data.txt"));

            //speed
            bw.write(String.valueOf(gamePanel.getPlayer().getSpeed()));
            bw.newLine();
            //strength
            bw.write(String.valueOf(gamePanel.getPlayer().getStrength()));
            bw.newLine();
            //max life
            bw.write(String.valueOf(gamePanel.getPlayer().getMaxLife()));
            bw.newLine();
            //coin
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void loadData(){
        try {
            BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\thanh\\Downloads\\project\\OOP-Project\\data.txt"));

            //speed
            String s = br.readLine();
            gamePanel.getPlayer().setSpeed(Integer.parseInt(s));
            //strength
            s = br.readLine();
            gamePanel.getPlayer().setStrength(Integer.parseInt(s));
            //max life
            s = br.readLine();
            gamePanel.getPlayer().setLife(Integer.parseInt(s));

            br.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}