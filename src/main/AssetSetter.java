package main;

import entity.monster.MON_GreenSlime;
import entity.npc.NPC_OldMan;
import entity.object.OBJ_Axe;
import entity.object.OBJ_Heart;
import entity.object.OBJ_ManaCrystal;
import entity.object.OBJ_Potion_Red;
import entity.object.OBJ_Shield_Blue;

public class AssetSetter {
    // Attribute
    private GamePanel gamePanel;

    // Constructor
    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    // Set game's assets
    public void setObject() {
        gamePanel.getObjects()[3] = new OBJ_Axe(gamePanel);
        gamePanel.getObjects()[3].setWorldX(gamePanel.getTileSize() * 33);
        gamePanel.getObjects()[3].setWorldY(gamePanel.getTileSize() * 21);

        gamePanel.getObjects()[4] = new OBJ_Shield_Blue(gamePanel);
        gamePanel.getObjects()[4].setWorldX(gamePanel.getTileSize() * 35);
        gamePanel.getObjects()[4].setWorldY(gamePanel.getTileSize() * 21);

        gamePanel.getObjects()[5] = new OBJ_Potion_Red(gamePanel);
        gamePanel.getObjects()[5].setWorldX(gamePanel.getTileSize() * 22);
        gamePanel.getObjects()[5].setWorldY(gamePanel.getTileSize() * 27);

        gamePanel.getObjects()[6] = new OBJ_Heart(gamePanel);
        gamePanel.getObjects()[6].setWorldX(gamePanel.getTileSize() * 22);
        gamePanel.getObjects()[6].setWorldY(gamePanel.getTileSize() * 29);

        gamePanel.getObjects()[7] = new OBJ_ManaCrystal(gamePanel);
        gamePanel.getObjects()[7].setWorldX(gamePanel.getTileSize() * 22);
        gamePanel.getObjects()[7].setWorldY(gamePanel.getTileSize() * 31);
    }

    public void setNPC() {
        gamePanel.getNPCs()[0] = new NPC_OldMan(gamePanel);
        gamePanel.getNPCs()[0].setWorldX(gamePanel.getTileSize() * 21);
        gamePanel.getNPCs()[0].setWorldY(gamePanel.getTileSize() * 21);
    }

    public void setMonster() {
        gamePanel.getMonsters()[0] = new MON_GreenSlime(gamePanel);
        gamePanel.getMonsters()[0].setWorldX(gamePanel.getTileSize() * 23);
        gamePanel.getMonsters()[0].setWorldY(gamePanel.getTileSize() * 36);

        gamePanel.getMonsters()[1] = new MON_GreenSlime(gamePanel);
        gamePanel.getMonsters()[1].setWorldX(gamePanel.getTileSize() * 23);
        gamePanel.getMonsters()[1].setWorldY(gamePanel.getTileSize() * 37);

        gamePanel.getMonsters()[2] = new MON_GreenSlime(gamePanel);
        gamePanel.getMonsters()[2].setWorldX(gamePanel.getTileSize() * 24);
        gamePanel.getMonsters()[2].setWorldY(gamePanel.getTileSize() * 36);

        gamePanel.getMonsters()[3] = new MON_GreenSlime(gamePanel);
        gamePanel.getMonsters()[3].setWorldX(gamePanel.getTileSize() * 24);
        gamePanel.getMonsters()[3].setWorldY(gamePanel.getTileSize() * 34);

        gamePanel.getMonsters()[4] = new MON_GreenSlime(gamePanel);
        gamePanel.getMonsters()[4].setWorldX(gamePanel.getTileSize() * 23);
        gamePanel.getMonsters()[4].setWorldY(gamePanel.getTileSize() * 34);

        gamePanel.getMonsters()[5] = new MON_GreenSlime(gamePanel);
        gamePanel.getMonsters()[5].setWorldX(gamePanel.getTileSize() * 20);
        gamePanel.getMonsters()[5].setWorldY(gamePanel.getTileSize() * 39);
    }
}