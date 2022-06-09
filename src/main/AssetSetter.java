package main;

import entity.monster.MON_GreenSlime;
import entity.monster.MON_Zombie;
import entity.monster.MON_GhostBat;
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
        gamePanel.getMonsters()[0] = new MON_GhostBat(gamePanel);
        gamePanel.getMonsters()[0].setWorldX(gamePanel.getTileSize() * 14);
        gamePanel.getMonsters()[0].setWorldY(gamePanel.getTileSize() * 9);

        gamePanel.getMonsters()[1] = new MON_GhostBat(gamePanel);
        gamePanel.getMonsters()[1].setWorldX(gamePanel.getTileSize() * 17);
        gamePanel.getMonsters()[1].setWorldY(gamePanel.getTileSize() * 29);

        gamePanel.getMonsters()[2] = new MON_GhostBat(gamePanel);
        gamePanel.getMonsters()[2].setWorldX(gamePanel.getTileSize() * 32);
        gamePanel.getMonsters()[2].setWorldY(gamePanel.getTileSize() * 36);

        // gamePanel.getMonsters()[3] = new MON_GhostBat(gamePanel);
        // gamePanel.getMonsters()[3].setWorldX(gamePanel.getTileSize() * 33);
        // gamePanel.getMonsters()[3].setWorldY(gamePanel.getTileSize() * 28);

        gamePanel.getMonsters()[4] = new MON_GreenSlime(gamePanel);
        gamePanel.getMonsters()[4].setWorldX(gamePanel.getTileSize() * 26);
        gamePanel.getMonsters()[4].setWorldY(gamePanel.getTileSize() * 26);

        gamePanel.getMonsters()[5] = new MON_GreenSlime(gamePanel);
        gamePanel.getMonsters()[5].setWorldX(gamePanel.getTileSize() * 28);
        gamePanel.getMonsters()[5].setWorldY(gamePanel.getTileSize() * 32);

        gamePanel.getMonsters()[6] = new MON_GreenSlime(gamePanel);
        gamePanel.getMonsters()[6].setWorldX(gamePanel.getTileSize() * 18);
        gamePanel.getMonsters()[6].setWorldY(gamePanel.getTileSize() * 32);

        gamePanel.getMonsters()[7] = new MON_GreenSlime(gamePanel);
        gamePanel.getMonsters()[7].setWorldX(gamePanel.getTileSize() * 14);
        gamePanel.getMonsters()[7].setWorldY(gamePanel.getTileSize() * 26);

        // gamePanel.getMonsters()[8] = new MON_GreenSlime(gamePanel);
        // gamePanel.getMonsters()[8].setWorldX(gamePanel.getTileSize() * 16);
        // gamePanel.getMonsters()[8].setWorldY(gamePanel.getTileSize() * 18);

        // gamePanel.getMonsters()[9] = new MON_GreenSlime(gamePanel);
        // gamePanel.getMonsters()[9].setWorldX(gamePanel.getTileSize() * 27);
        // gamePanel.getMonsters()[9].setWorldY(gamePanel.getTileSize() * 17);

        // gamePanel.getMonsters()[10] = new MON_GreenSlime(gamePanel);
        // gamePanel.getMonsters()[10].setWorldX(gamePanel.getTileSize() * 19);
        // gamePanel.getMonsters()[10].setWorldY(gamePanel.getTileSize() * 14);

        gamePanel.getMonsters()[11] = new MON_GreenSlime(gamePanel);
        gamePanel.getMonsters()[11].setWorldX(gamePanel.getTileSize() * 29);
        gamePanel.getMonsters()[11].setWorldY(gamePanel.getTileSize() * 13);

        gamePanel.getMonsters()[12] = new MON_GreenSlime(gamePanel);
        gamePanel.getMonsters()[12].setWorldX(gamePanel.getTileSize() * 38);
        gamePanel.getMonsters()[12].setWorldY(gamePanel.getTileSize() * 15);

        gamePanel.getMonsters()[13] = new MON_GreenSlime(gamePanel);
        gamePanel.getMonsters()[13].setWorldX(gamePanel.getTileSize() * 36);
        gamePanel.getMonsters()[13].setWorldY(gamePanel.getTileSize() * 10);

        gamePanel.getMonsters()[14] = new MON_Zombie(gamePanel);
        gamePanel.getMonsters()[14].setWorldX(gamePanel.getTileSize() * 38);
        gamePanel.getMonsters()[14].setWorldY(gamePanel.getTileSize() * 7);

        // gamePanel.getMonsters()[15] = new MON_Zombie(gamePanel);
        // gamePanel.getMonsters()[15].setWorldX(gamePanel.getTileSize() * 20);
        // gamePanel.getMonsters()[15].setWorldY(gamePanel.getTileSize() * 38);
    }
}