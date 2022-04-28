package entity.object;

import entity.Entity;
import entity.TYPE;
import main.GamePanel;

public class OBJ_Shield_Blue extends Entity {
    // Constructor
    public OBJ_Shield_Blue(GamePanel gamePanel) {
        super(gamePanel);

        type = TYPE.Shield;
        name = "Blue Shield";
        down1 = setup("/objects/shield_blue", gamePanel.getTileSize(), gamePanel.getTileSize());
        defenseValue = 2;
        description = "[" + name + "]\nA shiny blue shield.";
    }
}