package entity.monster;

import entity.Entity;
import entity.TYPE;
import main.GamePanel;

public class Monster extends Entity {
    // Attribute
    private GamePanel gamePanel;

    // Constructor
    public Monster(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        type = TYPE.Monster;
    }

    // Overridden methods
    @Override
    public void setAction() {
        if (alive == true && dying == false) {
            if (gamePanel.getPlayer().getWorldX() < worldX && gamePanel.getPlayer().getWorldY() < worldY)
                direction = "up-left";
            else if (gamePanel.getPlayer().getWorldX() < worldX && gamePanel.getPlayer().getWorldY() > worldY)
                direction = "down-left";
            else if (gamePanel.getPlayer().getWorldX() > worldX && gamePanel.getPlayer().getWorldY() > worldY)
                direction = "down-right";
            else if (gamePanel.getPlayer().getWorldX() > worldX && gamePanel.getPlayer().getWorldY() < worldY)
                direction = "up-right";
            else if (gamePanel.getPlayer().getWorldX() == worldX && gamePanel.getPlayer().getWorldY() < worldY)
                direction = "up";
            else if (gamePanel.getPlayer().getWorldX() < worldX && gamePanel.getPlayer().getWorldY() == worldY)
                direction = "left";
            else if (gamePanel.getPlayer().getWorldX() == worldX && gamePanel.getPlayer().getWorldY() > worldY)
                direction = "down";
            else if (gamePanel.getPlayer().getWorldX() > worldX && gamePanel.getPlayer().getWorldY() == worldY)
                direction = "right";
        }
    }
}