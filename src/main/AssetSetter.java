package main;

import entity.monster.MON_GreenSlime;
import entity.monster.MON_crow;
import entity.npc.NPC_Princess;
import entity.object.OBJ_Potion_Red;

public class AssetSetter {
    // Attribute
    private GamePanel gamePanel;

    // Constructor
    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    // Set game's assets
    public void setObject() {
        gamePanel.getObjects()[5] = new OBJ_Potion_Red(gamePanel);
        gamePanel.getObjects()[5].setWorldX(gamePanel.getTileSize() * 22);
        gamePanel.getObjects()[5].setWorldY(gamePanel.getTileSize() * 27);
    }

    public void setNPC() {
        gamePanel.getNPCs()[0] = new NPC_Princess(gamePanel);
        gamePanel.getNPCs()[0].setWorldX(gamePanel.getTileSize() * 23);
        gamePanel.getNPCs()[0].setWorldY(gamePanel.getTileSize() * 21);
    }

    public void setMonster() {
        gamePanel.getMonsters()[0] = new MON_crow(gamePanel);
        gamePanel.getMonsters()[0].setWorldX(gamePanel.getTileSize() * 23);
        gamePanel.getMonsters()[0].setWorldY(gamePanel.getTileSize() * 36);

        gamePanel.getMonsters()[1] = new MON_GreenSlime(gamePanel);
        gamePanel.getMonsters()[1].setWorldX(gamePanel.getTileSize() * 23);
        gamePanel.getMonsters()[1].setWorldY(gamePanel.getTileSize() * 37);

        gamePanel.getMonsters()[2] = new MON_crow(gamePanel);
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