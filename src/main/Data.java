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
            //speed Fee
            bw.write(String.valueOf(gamePanel.getPlayer().getSpeedUpgradeFee()));
            bw.newLine();
            //strength Fee
            bw.write(String.valueOf(gamePanel.getPlayer().getStrengthUpgradeFee()));
            bw.newLine();
            //HP fee
            bw.write(String.valueOf(gamePanel.getPlayer().getHpUpgradeFee()));
            bw.newLine();

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
            gamePanel.getPlayer().setSpeed(Double.parseDouble(s));
            //strength
            s = br.readLine();
            gamePanel.getPlayer().setStrength(Integer.parseInt(s));
            //max life
            s = br.readLine();
            gamePanel.getPlayer().setLife(Integer.parseInt(s));
            //speed fee
            s = br.readLine();
            gamePanel.getPlayer().setSpeedUpgradeFee(Integer.parseInt(s));
            //strength fee
            s = br.readLine();
            gamePanel.getPlayer().setStrengthUpgradeFee(Integer.parseInt(s));
            //max life fee
            s = br.readLine();
            gamePanel.getPlayer().setHpUpgradeFee(Integer.parseInt(s));


            br.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}